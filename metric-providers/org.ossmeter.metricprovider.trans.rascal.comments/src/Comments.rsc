module Comments

import analysis::m3::Core;
import analysis::m3::AST;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

import Prelude;
import util::Math;

import analysis::graphs::Graph;

data CommentStats
  = stats(int linesWithComment, int headerStart, int headerSize, int commentedOutCodeLines)
  | unknown()
  ;


@memo
CommentStats commentStats(list[str] lines, Language lang) {
  CommentStats result;

  switch(lang) {
    case java(): result = commentStats(lines, {<"/*", "*/">}, {"//"}, {";", "{", "}"}, {"package", "import"});
    case php(): result = commentStats(lines, {<"/*", "*/">}, {"//", "#"}, {";", "{", "}", "$"}, {"\<?php", "\<?"});
    default: result = unknown();
  }
  
  return result;
}

CommentStats commentStats(list[str] lines, rel[str, str] blockDelimiters, set[str] lineDelimiters, set[str] codePatterns, set[str] ignorePrefixesForHeader) {

  int cloc = 0, hskip = 0, hloc = 0, coloc = 0;
  
  containsCode = bool(str s) {
    return any(p <- codePatterns, findFirst(s, p) > -1);
  };
  
  inBlock = false;
  currentDelimiter = "";  

  bool inHeader = true;
  bool headerHasComments = false;
  for (l <- lines) {
    originalLine = l;
    hasCode = false;
    hasComment = false;
    
    if (inBlock) {
      hasComment = true;
    
      if (/<pre:.*><currentDelimiter><rest:.*>/ := l) {      
        hasCode = containsCode(pre);
        inBlock = false;
        l = rest;
      } else {
        hasCode = containsCode(l);
      }
    }

    if (!inBlock) {
      while (size(l) > 0) {
        matches =
          [ <size(pre), post, ""> | ld <- lineDelimiters, /<pre:.*><ld><post:.*>/ := l ] +
          [ <size(pre), post, cd> | <od, cd> <- blockDelimiters, /<pre:.*><od><post:.*>/ := l ];
        
        matches = sort(matches, bool(tuple[int, str, str] a, tuple[int, str, str] b) { return a[0] < b[0]; });
                  
        if (size(matches) > 0) {
          hasComment = true;
          m = matches[0];
          if (m[2] == "") { // line comment
            hasCode = hasCode || containsCode(m[1]);
            l = "";
          } else { // start of block comment
            currentDelimiter = m[2];
            if (/<c:.*><currentDelimiter><rest:.*>/ := m[1]) { // block closed on same line
              l = rest;
              hasCode = hasCode || containsCode(c);
            } else { // block open at end of line
              hasCode = hasCode || containsCode(m[1]);
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
      if (inHeader) {
        hloc += 1;
        headerHasComments = true;
      }
    } else if (inHeader) {
      if (headerHasComments) {
        inHeader = false;
      } else {
        // skip trailing empty lines or lines with prefixes to ignore  
        trimmed = trim(originalLine);
        if (size(trimmed) == 0 || 
           (size(ignorePrefixesForHeader) > 0 && any(p <- ignorePrefixesForHeader, startsWith(trimmed, p)))) {
          hskip += 1;
        } else {
          inHeader = false;
        }
      }
    }
    
    if (hasCode) {
      coloc += 1;
    }    
  }

  return stats(cloc, hskip, hloc, coloc);
}

@metric{commentLOC}
@doc{Number of lines containing comments per file}
@friendlyName{Number of lines containing comments per file}
@appliesTo{generic()}
map[loc, int] commentLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f])
    {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.linesWithComment;
      }
    }
  }

  return result;
}

@metric{headerLOC}
@doc{Header size per file}
@friendlyName{Header size per file}
@appliesTo{generic()}
map[loc, int] headerLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f])
    {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.headerSize;
      }
    }
  }

  return result;
}

@metric{commentedOutCode}
@doc{Lines of commented out code per file}
@friendlyName{Lines of commented out code per file}
@appliesTo{generic()}
map[loc, int] commentedOutCode(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<lang, f, _> <- asts, lang != generic()) {
    if ({lines(l)} := asts[generic(), f])
    {
      s = commentStats(l, lang);
      if (s != unknown()) {
        result[f] = s.commentedOutCodeLines;
      }
    }
  }

  return result;
}


@metric{commentedOutCodePerLanguage}
@doc{Lines of commented out code per language}
@friendlyName{Lines of commented out code per language}
@appliesTo{generic()}
@uses{("commentedOutCode": "commentedOutCode")}
map[str, int] commentedOutCodePerLanguage(rel[Language, loc, AST] asts = {}, map[loc, int] commentedOutCode = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic(), f in commentedOutCode) {
    result["<l>"]?0 += commentedOutCode[f];
  }
  return result;
}


@metric{percentageCommentedOutCode}
@doc{Percentage of commented out code}
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
@doc{Number of lines containing comments per language (excluding headers)}
@friendlyName{Number of lines containing comments per language (excluding headers)}
@appliesTo{generic()}
@uses{("commentLOC": "commentLOC",
       "headerLOC": "headerLOC")}
map[str, int] commentLinesPerLanguage(rel[Language, loc, AST] asts = {}, map[loc, int] commentLOC = (), map[loc, int] headerLOC = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic(), f in commentLOC) {
    result["<l>"]?0 += commentLOC[f] - (headerLOC[f]?0);
  }
  return result;
}


@metric{commentPercentage}
@doc{Percentage of lines with comments (excluding headers)}
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
  
  // TODO verify star ratings
  
  if (totalPercentage >= 15) {
    stars = \four();
  } else if (totalPercentage >= 10) {
    stars = three();
  } else if (totalPercentage >= 1) {
    stars = two();
  }
  
  txt = "The percentage of lines containing comments (excluding headers) over all measured languages is <totalPercentage>%.";

  languagePercentage = ( l : 100 * commentLinesPerLanguage[l] / toReal(locPerLanguage[l]) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l[0]> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}


@metric{headerUse}
@doc{Percentage of files with headers}
@friendlyName{Percentage of files with headers}
@appliesTo{generic()}
@uses{("headerLOC": "headerLOC")}
real headerUse(map[loc, int] headerLOC = ()) {
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

public set[set[&T]] connectedComponents(Graph[&T] graph) { // TODO move to rascal graph library
       set[set[&T]] components = {};

       Graph[&T] undirected = graph + invert(graph);

       set[&T] todo = domain(undirected);

       while (size(todo) > 0) {
	     component = reach(undirected, {getOneFrom(todo)});
	     components += {component};
	     todo -= component;
       };

       return components;
}

@metric{uniqueHeaders}
@doc{Number of estimated unique headers}
@friendlyName{Number of estimated unique headers}
@appliesTo{generic()}
int uniqueHeaders(rel[Language, loc, AST] asts = {}) {

	// extract headers
	set[Header] headers = {};
	
	for (<lang, f, _> <- asts, lang != generic()) {
		if ({lines(l)} := asts[generic(), f])
		{
			s = commentStats(l, lang);
			if (s != unknown() && s.headerSize > 0) {
				headers += {extractHeader(l, s.headerSize, s.headerStart)};
			}
		}
	}
	
	// group headers
	
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

	return unmatched + size(components);
}

