module LOC

import lang::java::m3::Core;
import lang::java::php::Core;
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

real giniLOC(map[loc, int] locs) {
  return gini([<0,0>] + toList(distribution(locs)));
}

@metric{genericLOCoverFiles}
@doc{Language independent physical lines of code over files}
@friendlyName{Language independent physical lines of code over files}
@appliesTo{generic()}
real giniLOCOverFiles(rel[Language, loc, AST] asts = {}) {
  return giniLOC(countLoc(asts=asts));
}

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

@metric{LOCoverPHPClass}
@doc{Physical lines of code over PHP classes and interfaces}
@friendlyName{Physical lines of code over PHP classes and interfaces}
@appliesTo{php()}
real giniLOCOverClassPHP(rel[Language, loc, M3] m3s = {}) {
  return giniLOCOverClass({m3 | <php(), _, m3> <- m3s}, 
  	bool(loc lc) {
  		return lang::php::m3::Core::isInterface(lc) || lang::php::m3::Core::isClass(lc);
  	});
}
