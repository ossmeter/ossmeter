module Comments

import analysis::m3::Core;
import analysis::m3::AST;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

import Prelude;

int countCommentedOutLOC(AST ast, rel[str, str] blockDelimiters, set[str] lineDelimiters, set[str] codePatterns) {

  set[str] lines;
  int cloc = 0;

  if (lines(l) := ast) {
    lines = l;
  } else {
    return 0;
  }
  
  containsCode = bool(str s) {
    return any(p <- codePatterns, /.*<p>.*/ := s);
  };
  
  inBlock = false;
  currentDelimiter = "";  

  for (l <- lines) {
    hasCode = false;
    
    if (inBlock) {
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

    if (hasCode) {
      cloc += 1;
    }
  }

  return cloc;
}

@metric{commentedOutCode}
@doc{Lines of commented out code}
@friendlyName{Lines of commented out code}
@appliesTo{generic()}
map[loc, int] commentedOutCode(rel[Language, loc, AST] asts = {}) {
  map[loc, int] result = ();

  for (<l, f, a> <- asts, l != generic()) {
    cloc = 0;
    switch(l) {
      case java(): cloc = countCommentedOutLOC(a, {<"/*", "*/">}, {"//"}, {";", "{", "}"});
      case php(): cloc = countCommentedOutLOC(a, {<"/*", "*/">}, {"//", "#"}, {";", "{", "}", "$"});
      default: continue;
    }
    result[f] = cloc;
  }

  return result;
}


@metric{commentedOutCodePerLanguage}
@doc{Lines of commented out code per language}
@friendlyName{Lines of commented out code per language}
@appliesTo{generic()}
@uses{("commentedOutCode" :"commentedOutCode")}
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
@uses{("locPerLanguage" :"locPerLanguage")}
@uses{("commentedOutCodePerLanguage" :"commentedOutCodePerLanguage")}
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

