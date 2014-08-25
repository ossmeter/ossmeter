module Comments

import analysis::m3::Core;
import analysis::m3::AST;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

import Prelude;

data CommentStats
  = stats(int linesWithComment, int linesInHeader, int commentedOutCodeLines)
  | unknown()
  ;


@memo
CommentStats commentStats(list[str] lines, Language lang) {
  switch(lang) {
    case java(): return commentStats(a, {<"/*", "*/">}, {"//"}, {";", "{", "}"});
    case php(): return commentStats(a, {<"/*", "*/">}, {"//", "#"}, {";", "{", "}", "$"});
    default: return unknown();
  }
}

CommentStats commentStats(list[str] lines, rel[str, str] blockDelimiters, set[str] lineDelimiters, set[str] codePatterns) {

  int cloc = 0, hloc = 0, coloc = 0;
  
  containsCode = bool(str s) {
    return any(p <- codePatterns, findFirst(s, p) > -1);
  };
  
  inBlock = false;
  currentDelimiter = "";  

  bool firstLine = true;
  bool inHeader = true;
  for (l <- lines) {
    hasCode = false;
    hasComment = false;
    
    if (inBlock) {
      hasComment = true;
      if (inHeader) {
        hloc += 1;
      }
    
      if (/<pre:.*><currentDelimiter><rest:.*>/ := l) {      
        hasCode = containsCode(pre);
        inBlock = false;
        l = rest;
      } else {
        hasCode = containsCode(l);
      }
    }

    if (!inBlock) {
      if (!firstLine) {
        inHeader = false;
      }
    
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
            if (firstLine) {
              hloc += 1;
            }            
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
    }
    if (hasCode) {
      coloc += 1;
    }
    
    firstLine = false;
  }

  return stats(cloc, hloc, coloc);
}

@metric{commentLOC}
@doc{Number of lines containing comments per file}
@friendlyName{Number of lines containing comments per file}
@appliesTo{generic()}
map[loc, int] commentLOC(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<l, f, _> <- asts, l != generic()) {
    if ({a} := asts[generic(), f])
    {
      stats = commentStats(a, l);
      if (stats != unknown()) {
        result[f] = stats.linesWithComment;
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

  for (<l, f, _> <- asts, l != generic()) {
    if ({a} := asts[generic(), f])
    {
      stats = commentStats(a, l);
      if (stats != unknown()) {
        result[f] = stats.linesInHeader;
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

  for (<l, f, _> <- asts, l != generic()) {
    if ({a} := asts[generic(), f])
    {
      stats = commentStats(a, l);
      if (stats != unknown()) {
        result[f] = stats.commentedOutCodeLines;
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
@uses{("locPerLanguage": "locPerLanguage")}
@uses{("commentedOutCodePerLanguage": "commentedOutCodePerLanguage")}
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
  
  txt = "The percentage of commented out code over all measured languages is <totalPercentage>%";

  languagePercentage = ( l : 100 * commentedOutCodePerLanguage[l] / toReal(locPerLanguage[l]) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l[0]> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}


@metric{commentLinesPerLanguage}
@doc{Number of lines containing comments per language (excluding headers)}
@friendlyName{Number of lines containing comments per language (excluding headers)}
@appliesTo{generic()}
@uses{("commentLOC": "commentLOC")}
@uses{("headerLOC": "headerLOC")}
map[str, int] commentLOCPerLanguage(rel[Language, loc, AST] asts = {}, map[loc, int] commentLOC = (), map[loc, int] headerLOC = ()) {
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
@uses{("locPerLanguage": "locPerLanguage")}
@uses{("commentLinesPerLanguage": "commentLinesPerLanguage")}
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
  
  txt = "The percentage of comments (excluding headers) over all measured languages is <totalPercentage>%";

  languagePercentage = ( l : 100 * commentLinesPerLanguage[l] / toReal(locPerLanguage[l]) | l <- languages ); 

  otherTxt = intercalate(", ", ["<l[0]> (<languagePercentage[l]>%)" | l <- languages]);  
  txt += " The percentages per language are <otherTxt>.";

  return factoid(txt, stars);	
}

