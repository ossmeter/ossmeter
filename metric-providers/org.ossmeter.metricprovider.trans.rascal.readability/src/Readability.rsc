module Readability

import org::ossmeter::metricprovider::MetricProvider;

import String;
import Set;
import util::Math;

import analysis::m3::Core;
import analysis::m3::AST;

@doc{
	Checks the appearance of whitespace in lines of code around given symbols.
	Returns the ratio of found spaces v.s. total spaces possible.
}
public real checkSpaces(list[str] lines, set[str] symbolsTwoSides, set[str] symbolsOneSide) {
	
	map[str, int] whitespaceRequired = toMapUnique(symbolsTwoSides * {2} + symbolsOneSide * {1});
	
	spacesFound = 0;
	spacesMissed = 0;
	
	whitespace = {" ", "\n", "\r", "\f", "\t"};
	
	for (l <- lines) {
		for (s <- whitespaceRequired) {
			for (p <- findAll(l, s)) {
				spaces = 0;
				if (p == 0 || l[p-1] in whitespace) {
					spaces += 1;
				}
				p += size(s); // p is after s
				if (p == size(l) || l[p] in whitespace) {
					spaces += 1;
				}
				
				missed = max(0, whitespaceRequired[s] - spaces);
				
				spacesFound += whitespaceRequired[s] - missed;
				spacesMissed += missed;
			}
		}
	}
	
	maxSpacesPossible = spacesFound + spacesMissed;
	
	if (maxSpacesPossible > 0) {
		return spacesFound / toReal(maxSpacesPossible);
	}
	
	return 1.0;
}


//real checkSpacesGeneric(list[str] lines, Language lang) {
//  real result;
//
//  switch(lang) {
//    case java(): result = checkSpaces(lines, {"{", "}"}, {";", ","});
//    case php(): result = checkSpaces(lines, {"{", "}"}, {";", ","});
//    default: result = -1.0;
//  }
//  
//  return result;
//}


@metric{fileReadability}
@doc{Code readability per file, measured by use of whitespace}
@friendlyName{FileReadability}
@appliesTo{generic()}
map[loc, real] fileReadability(rel[Language, loc, AST] asts = {}) {
  return (f : checkSpaces(l, {"{", "}"}, {";", ","}) 
         | <generic(), f, lines(l)> <- asts
         , f.extension == "java" || f.extension == "php");
}

@metric{ReadabilityFactoid}
@doc{Check for proper use of whitespace}
@friendlyName{Use of whitespace}
@appliesTo{generic()}
@uses=("fileReadability":"fileReadability")
Factoid readabilityFactoid(map[loc, real] fileReadability) {
  re = {<l,fileReadability[l]> | l <- fileReadability };
  
  allgood  = [ r | <_,r> <- re, r == 1];
  oneperc  = [ r | <_,r> <- re, r < 1 && r >= 0.99];
  tenperc  = [ r | <_,r> <- re, r < 0.99 && r >= 0.90];
  moreperc = [ r | <_,r> <- re, r < 0.9];

  med = median(oneperc + tenperc + moreperc);
  
  total = size(fileReadability);
  star = \one();
  
  txt = "For readability of source code it is import that spaces around delimiters such as [,;{}] are used.\n";
  
  if (size(allgood) == total) {
     txt += "In this project all spaces in all files help in readability of the source code";
     star = \four();
  }
  else if (size(moreperc) == 0 &&  size(tenperc) == 0 && size(oneperc) <= (total / 10)) {
     // if less than 10% of the files have 1% problems, we still give three stars
     star = \three();
     txt += "In this project less than 10% of the files have minor readability issues.
            'An average file in this category has <med> of the spaces in the wrong place (either no space or too many spaces).";
  }
  else if (size(moreperc) == 0 && size(tenperc) <= (total / 10)) {
     star = \two();
     txt += "In this project less than 10% of the files have major readability issues.
            'An average file in this category has <med> of the spaces in the wrong place (either no space or too many spaces).";
  }
  else {
     star = \one();
     txt += "In this project, <percent(size(moreperc) + size(tenperc) + size(oneperc), total)> of the files readability issues.
            'An average file has <med> of the spaces in the wrong place (either no space or too many spaces).";
  }
  
  return factoid(txt, star);
}

