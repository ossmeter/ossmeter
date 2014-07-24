module LOCJava

import lang::java::m3::Core;
import analysis::graphs::Graph;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

import Prelude;

import LOC;

real giniLOCOverClass(set[M3] m3s, bool(loc) isClass) {
  classLines = (lc : sc.end.line - sc.begin.line + 1 | m3 <- m3s, <lc, sc> <- m3@declarations, isClass(lc));
  return giniLOC(classLines);
}

@metric{LOCoverJavaClass}
@doc{Physical lines of code over Java classes, interfaces and enums}
@friendlyName{Physical lines of code over Java classes, interfaces and enums}
@appliesTo{java()}
real giniLOCOverClassJava(rel[Language, loc, M3] m3s = {}) {
  return giniLOCOverClass({m3 | <java(), _, m3> <- m3s}, 
  	bool(loc lc) {
  		return lang::java::m3::Core::isInterface(lc) || lang::java::m3::Core::isClass(lc) || lc.scheme == "java+enum";
  	});
}