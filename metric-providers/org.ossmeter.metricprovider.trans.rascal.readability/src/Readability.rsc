module Readability

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


real checkSpacesGeneric(list[str] lines, Language lang) {
  real result;

  switch(lang) {
    case java(): result = checkSpaces(lines, {"{", "}"}, {";", ","});
    case php(): result = checkSpaces(lines, {"{", "}"}, {";", ","});
    default: result = -1.0;
  }
  
  return result;
}


@metric{FileReadability}
@doc{Code readability per file, measured by use of whitespace}
@friendlyName{FileReadability}
@appliesTo{generic()}
map[loc, real] fileReadability(rel[Language, loc, AST] asts = {}) {
  map[loc, real] result = ();
  
  for (<lang, f, _> <- asts, lang != generic(), {lines(l)} := asts[generic(), f]) {
    r = checkSpacesGeneric(l, lang);
    if (r >= 0.0) {
    	result[f] = r;
    }
  }
  return result;
}


