module LOC

import analysis::m3::Core;
import analysis::m3::AST;
import analysis::graphs::Graph;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

import Prelude;


@metric{genericLOC}
@doc{loc}
@friendlyName{Language independent physical lines of code}
@appliesTo{generic()}
map[loc, int] countLoc(rel[Language, loc, AST] asts = {}) {
  return (f:size(ls) | <generic(), f, lines(ls)> <- asts);
}

real giniLOC(map[loc, int] locs) {
  dist = distribution(locs);
  if (size(dist) < 1) {
  	return -1.0; // TODO how can we return no result at all?
  }
  return gini([<0,0>] + [<x, dist[x]> | x <- dist]);
}

@metric{genericLOCoverFiles}
@doc{Language independent physical lines of code over files}
@friendlyName{Language independent physical lines of code over files}
@appliesTo{generic()}
real giniLOCOverFiles(rel[Language, loc, AST] asts = {}) {
  return giniLOC(countLoc(asts=asts));
}

