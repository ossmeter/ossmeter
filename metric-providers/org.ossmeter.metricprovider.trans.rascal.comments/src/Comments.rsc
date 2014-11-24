module Comments

import analysis::m3::Core;
import analysis::m3::AST;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

import Prelude;
import util::Math;
import analysis::graphs::Graph;
import Split;

data CommentStats
  = stats(int linesWithComment, int headerStart, int headerSize, int commentedOutCodeLines)
  | unknown()
  ;


@memo
CommentStats commentStats(list[str] lines, Language lang) {
  CommentStats result;

  switch(lang) {
    case java(): result = genericCommentStats(lines, {<"/*", "*/">}, {"//"}, {";", "{", "}"}, {"package", "import"});
    case php(): result = genericCommentStats(lines, {<"/*", "*/">}, {"//", "#"}, {";", "{", "}", "$"}, {"\<?php", "\<?"});
    default: result = unknown();
  }
  
  return result;
}

CommentStats genericCommentStats(list[str] lines, rel[str, str] blockDelimiters, set[str] lineDelimiters, set[str] codePatterns, set[str] ignorePrefixesForHeader) {

  allDelimiters = lineDelimiters + domain(blockDelimiters);

  int cloc = 0, hskip = 0, hloc = 0, coloc = 0;
  
  containsCode = bool(str s) { // s is not a comment
    return /.*[a-zA-Z].*/ := s;
  };
  
  commentContainsCode = bool(str comment) {
    return size(codePatterns) > 0 && any(p <- codePatterns, findFirst(comment, p) > -1);
  };
    
  inBlock = false;
  currentDelimiter = "";  

  bool inHeader = true;
  for (l <- lines) {
    originalLine = l;
    hasCode = false;
    hasCodeInComment = false;
    hasComment = false;
    
    if (inBlock) {
      hasComment = true;
    
      if (<true, pre, rest> := firstSplit(l, currentDelimiter)) {      
        hasCodeInComment = commentContainsCode(pre);
        inBlock = false;
        l = rest;
      } else {
        hasCodeInComment = commentContainsCode(l);
      }
    }

    if (!inBlock) {
      while (l != "") {
        if (<true, pre, post, d> := firstSplit(l, allDelimiters)) {
          hasComment = true;
          hasCode = hasCode || containsCode(pre);
          if (d in lineDelimiters) { // line comment
            hasCodeInComment = hasCodeInComment || commentContainsCode(post);
            l = "";
          } else { // start of block comment
            currentDelimiter = getOneFrom(blockDelimiters[d]);
            if (<true, c, rest> := firstSplit(post, currentDelimiter)) { // block closed on same line
              l = rest;
              hasCodeInComment = hasCodeInComment || commentContainsCode(c);
            } else { // block open at end of line
              hasCodeInComment = hasCodeInComment || commentContainsCode(post);
              inBlock = true;
              l = "";
            }
          }
        } else {
          l = "";
        }
      }
    }

    if (hasComment) {
      cloc += 1;
    }

    if (hasCodeInComment) {
      coloc += 1;
    }
    
    if (inHeader) {
      trimmed = trim(originalLine);
      if ((hloc == 0) && (size(trimmed) == 0 || 
          (size(ignorePrefixesForHeader) > 0 && any(p <- ignorePrefixesForHeader, startsWith(trimmed, p))))) {
        // skip trailing empty lines or lines with prefixes to ignore
        hskip += 1;
      } else if (hasComment && !hasCode) {
        hloc += 1;
      } else {
        inHeader = false;
      }
    }
  }

  return stats(cloc, hskip, hloc, coloc);
}

@metric{commentLOC}
@doc{Number of lines containing comments per file is a basic metric used for downstream processing. This metric does not consider the difference
between natural language comments and commented out code.}
@friendlyName{Number of lines containing comments per file}
@appliesTo{generic()}
map[loc, int] commentLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f]) {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.linesWithComment;
      }
    }
  }

  return result;
}

@metric{headerLOC}
@doc{Header size per file is a basic metric counting the size of the comment at the start of each file. It is used for further processing downstream.}
@friendlyName{Header size per file}
@appliesTo{generic()}
map[loc, int] headerLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f]) {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.headerSize;
      }
    }
  }

  return result;
}

@metric{commentedOutCode}
@doc{Lines of commented out code per file uses heuristics (frequency of certain substrings typically used in code and not in natural language) to find out how
much source code comments are actually commented out code. Commented out code is, in large quantities is a quality contra-indicator.}
@friendlyName{Lines of commented out code per file}
@appliesTo{generic()}
map[loc, int] commentedOutCode(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f]) {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.commentedOutCodeLines;
      }
    }
  }

  return result;
}


@metric{commentedOutCodePerLanguage}
@doc{Lines of commented out code per file uses heuristics (frequency of certain substrings typically used in code and not in natural language) to find out how
much source code comments are actually commented out code. Commented out code is, in large quantities is a quality contra-indicator.}
@friendlyName{Lines of commented out code per language}
@appliesTo{generic()}
@uses{("commentedOutCode": "commentedOutCode")}
@historic
map[str, int] commentedOutCodePerLanguage(rel[Language, loc, AST] asts = {}, map[loc, int] commentedOutCode = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic(), f in commentedOutCode) {
    result["<getName(l)>"]?0 += commentedOutCode[f];
  }
  return result;
}


@metric{percentageCommentedOutCode}
@doc{Commented-out code, in large quantities, is a contra-indicator for quality being a sign of experimental code or avoiding the use of a version control system.}
@friendlyName{Percentage of commented out code}
@appliesTo{generic()}
@uses{("org.ossmeter.metricprovider.trans.rascal.LOC.locPerLanguage": "locPerLanguage",
       "commentedOutCodePerLanguage": "commentedOutCodePerLanguage")}
Factoid percentageCommentedOutCode(map[str, int] locPerLanguage = (), map[str, int] commentedOutCodePerLanguage = ()) {
  // only report figures for measured languages
  languages = domain(locPerLanguage) & domain(commentedOutCodePerLanguage);

  if (isEmpty(languages)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  totalLines = toReal(sum([ locPerLanguage[l] | l <- languages ]));
  totalCommentedLines = toReal(sum([ commentedOutCodePerLanguage[l] | l <- languages ]));

  totalPercentage = (totalCommentedLines / totalLines) * 100.0;
  
  stars = four();
  
  if (totalPercentage >= 5) {
    stars = \one();
  } else if (totalPercentage >= 2) {
    stars = two();
  } else if (totalPercentage >= 1) {
    stars = three();
  }
  
  txt = "The percentage of commented out code over all measured languages is <totalPercentage>%.";

  languagePercentage = ( l : 100 * commentedOutCodePerLanguage[l] / toReal(locPerLanguage[l]) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l[0]> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}


@metric{commentLinesPerLanguage}
@doc{Number of lines containing comments per language (excluding headers). The balance between comments and code indicates understandability. Too many comments are often not maintained and may lead to confusion, not enough means the code lacks documentation explaining its intent. This is a basic fact collection metric which is used further downstream.}
@friendlyName{Number of lines containing comments per language (excluding headers)}
@appliesTo{generic()}
@uses{("commentLOC": "commentLOC",
       "headerLOC": "headerLOC",
       "commentedOutCode": "commentedOutCode")}
map[str, int] commentLinesPerLanguage(rel[Language, loc, AST] asts = {},
                                      map[loc, int] commentLOC = (),
                                      map[loc, int] headerLOC = (),
                                      map[loc, int] commentedOutCode = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic(), f in commentLOC) {
    result["<getName(l)>"]?0 += commentLOC[f] - (headerLOC[f]?0) - (commentedOutCode[f]?0);
  }
  return result;
}


@metric{commentPercentage}
@doc{The balance between comments and code indicates understandability. Too many comments are often not maintained and may lead to confusion, not enough means the code lacks documentation explaining its intent.}
@friendlyName{Percentage of lines with comments (excluding headers)}
@appliesTo{generic()}
@uses{("org.ossmeter.metricprovider.trans.rascal.LOC.locPerLanguage": "locPerLanguage",
       "commentLinesPerLanguage": "commentLinesPerLanguage")}
Factoid commentPercentage(map[str, int] locPerLanguage = (), map[str, int] commentLinesPerLanguage = ()) {
  // only report figures for measured languages
  languages = domain(locPerLanguage) & domain(commentLinesPerLanguage);

  if (isEmpty(languages)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  totalLines = toReal(sum([ locPerLanguage[l] | l <- languages ]));
  totalCommentedLines = toReal(sum([ commentLinesPerLanguage[l] | l <- languages ]));

  totalPercentage = (totalCommentedLines / totalLines) * 100.0;
  
  stars = \one();
  
  // according to Software Assessments, Benchmarks, and Best Practices. Capers Jones 2000,
  // the ideal comment density is 1 comment per 10 statements. (less AND more is worse).
  // (which is around 9% if all lines would be either statements or comments)
  
  // according to The comment density of open source software code. Arafat and Riehle, 2009,
  // the average comment density of >5000 OSS projects is 18.67%
    
  if (totalPercentage < 25) {
  	if (totalPercentage > 8) {
  		stars = four();
  	}
  	else if (totalPercentage > 6) {
  		stars = three();
  	}
  	else if (totalPercentage > 4) {
  		stars = two();
  	}
  }
  else {
  	stars = three();
  }
  
  txt = "The percentage of lines containing comments over all measured languages is <totalPercentage>%. Headers and commented out code are not included in this measure.";

  languagePercentage = ( l : 100 * commentLinesPerLanguage[l] / toReal(locPerLanguage[l]) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}


@metric{headerPercentage}
@doc{Percentage of files with headers is an indicator for the amount of files which have been tagged with a copyright statement (or not). If the number is low this indicates a problem with the copyright of the program. Source files without a copyright statement are not open-source, they are owned, in principle, by the author and may not be copied without permission. Note that the existence of a header does not guarantee the presence of an open-source license, but its absence certainly is telling.}
@friendlyName{Percentage of files with headers.}
@appliesTo{generic()}
@uses{("headerLOC": "headerLOC")}
@historic
real headerPercentage(map[loc, int] headerLOC = ()) {
	int measuredFiles = size(headerLOC);
	if (measuredFiles == 0) {
		throw undefined("No headers found", |unknown:///|); 
	}	
	return (100.0 * ( 0 | it + 1 | f <- headerLOC, headerLOC[f] > 0 ) ) / measuredFiles;
}

private alias Header = set[str];

private Header extractHeader(list[str] lines, int headerSize, int headerStart) {
	return { trim(l) | l <- lines[headerStart..(headerStart + headerSize)], /.*[a-zA-Z].*/ := l, !contains(l, "@")};
}

@memo
private map[loc, Header] extractHeaders(rel[Language, loc, AST] asts) {
	map[loc, Header] headers = ();
	
	for (<lang, f, _> <- asts, lang != generic()) {
		if ({lines(l)} := asts[generic(), f])
		{
			s = commentStats(l, lang);
			if (s != unknown() && s.headerSize > 0) {
				headers[f] = extractHeader(l, s.headerSize, s.headerStart);
			}
		}
	}
	
	return headers;
}


@metric{headerCounts}
@doc{In principle it is expected for the files in a project to share the same license. The license text in the header of each file may differ slightly due to different copyright years and or lists of contributors. The heuristic allows for slight differences. The metric produces the number of different types of header files found. A high number is a contra-indicator, meaning either a confusing licensing scheme or the source code of many different projects is included in the code base of the analyzed system.}
@friendlyName{Number of appearances of estimated unique headers}
@appliesTo{generic()}
list[int] headerCounts(rel[Language, loc, AST] asts = {}) {

	headersPerFile = extractHeaders(asts);
	
	headers = range(headersPerFile);
	
	Graph[Header] bestMatches = {};
	
	int unmatched = 0;
	
	for (h1 <- headers) {
		int score = 0;
		Header bestMatch = {};
		
		for (h2 <- headers) {
			int s = size(h1 & h2);
			
			if (s > score) {
				score = s;
				bestMatch = h2;
			}
		}
		
		if (score > 0) {
			bestMatches += {<h1, bestMatch>};
		} else {
			unmatched += 1;
		}
	}
		
	components = connectedComponents(bestMatches);
	
	map[set[Header], int] headerCount = ();
	
	for (f <- headersPerFile) {
		h = headersPerFile[f];
		for (c <- components) {
			if (h in c) {
				headerCount[c]?0 += 1;
			}
		}
	}
	
	list[int] headerHistogram = [ headerCount[c] | c <- headerCount ] + [ 1 | i <- [0..unmatched] ]; 
	
	return headerHistogram;
}


@metric{headerUse}
@doc{In principle it is expected for the files in a project to share the same license. The license text in the header of each file may differ slightly due to different copyright years and or lists of contributors. We find out how many different types of header files are used and if the distribution is flat or focused on a single distribution. The difference is between  a clear and simple license for the entire project or a confusing licensing scheme with possible juridical consequences.}
@friendlyName{Consistency of header use}
@appliesTo{generic()}
@uses{("headerCounts": "headerCounts", "headerPercentage": "headerPercentage")}
Factoid headerUse(list[int] headerCounts = [], real headerPercentage = -1.0) {

	if (headerCounts == [] || headerPercentage == -1.0) {
		throw undefined("Not enough header data available", |tmp:///|);
	}

	stars = 1;
	message = "";

	if (headerPercentage > 50.0) {
		stars += 1;
		
		if (headerPercentage > 95.0) {
			stars += 1;
		}
		
		highestSimilarity = max(headerCounts) / sum(headerCounts);
		
		if (highestSimilarity > 0.8) {
			stars += 1;
		}
		
		message = "The percentage of files with a header is <headerPercentage>%." +
			"The largest group of similar headers spans <highestSimilarity>% of the files.";
	}
	else if (headerPercentage > 0.0) {
		message = "Only <headerPercentage>% of the files contain a header.";
	}
	else {
		message = "No headers found.";
	}
	
	return factoid(message, starLookup[stars]);	
}
