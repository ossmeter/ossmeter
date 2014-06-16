module LOC

import lang::java::m3::Core;
import analysis::graphs::Graph;
import IO;
import List;
import String;
import Set;
import ValueIO;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

@metric{loc}
@doc{loc}
@friendlyName{loc}
@appliesTo{generic()}
map[loc, int] countLoc(rel[Language, loc, M3] m3s = {}) {
  return (f:f.end.line | <_, m3> <- m3s[generic()], f <- range(m3@declarations));
}

@metric{locoverfiles}
@doc{locoverfiles}
@friendlyName{locoverfiles}
@appliesTo{generic()}
real giniLOCOverFiles(rel[Language, loc, M3] m3s = {}) {
  map[loc, int] locMap = countLoc(m3s=m3s);
    
  distLOCOverFiles = distribution(locMap);  
  
  return gini([<0,0>]+[<x, distLOCOverFiles[x]> | x <- distLOCOverFiles]);
}

@metric{locoverclass}
@doc{locoverclass}
@friendlyName{locoverclass}
@appliesTo{java()}
real giniLOCOverClass(rel[Language, loc, M3] m3s = {}) {
  map[loc class, int lines] result = ();

  for (<_, m3> <- m3s[java()]) {
    result += (lc : sc.end.line - sc.begin.line + 1 | <lc, sc> <- m3@declarations, isInterface(lc) || isClass(lc) || lc.scheme == "java+enum");
  }
  
  distLOCOverClass = distribution(result);
  return gini([<0,0>]+[<x, distLOCOverClass[x]> | x <- distLOCOverClass]);
}