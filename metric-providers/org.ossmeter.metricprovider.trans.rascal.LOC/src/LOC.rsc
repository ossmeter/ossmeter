module LOC

import lang::java::m3::Core;
import Java;
import analysis::graphs::Graph;
import analysis::m3::AST;
import IO;
import List;
import String;
import Set;
import ValueIO;
import Relation;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

@metric{genericLOC}
@doc{loc}
@friendlyName{Language independent physical lines of code}
@appliesTo{generic()}
map[loc, int] countLoc(rel[Language, loc, AST] asts = {}) {
  return (f:size(ls) | <generic(), _, lines(ls)> <- asts);
}

@metric{genericLOCoverFiles}
@doc{locoverfiles}
@friendlyName{Language independent physical lines of code over files}
@appliesTo{generic()}
real giniLOCOverFiles(rel[Language, loc, AST] asts = {}) {
  map[loc, int] locMap = countLoc(asts=asts);
    
  distLOCOverFiles = distribution(locMap);  
  
  return gini([<0,0>]+[<x, distLOCOverFiles[x]> | x <- distLOCOverFiles]);
}

@metric{LOCoverClass}
@doc{locoverclass}
@friendlyName{Physical lines of code over class}
@appliesTo{java()}
real giniLOCOverClass(rel[Language, loc, M3] m3s = {}) {
  map[loc class, int lines] result = ();

  for (<java(), _, m3> <- m3s) {
    result += (lc : sc.end.line - sc.begin.line + 1 | <lc, sc> <- m3@declarations, isInterface(lc) || isClass(lc) || lc.scheme == "java+enum");
  }
  
  distLOCOverClass = distribution(result);
  return gini([<0,0>]+[<x, distLOCOverClass[x]> | x <- distLOCOverClass]);
}