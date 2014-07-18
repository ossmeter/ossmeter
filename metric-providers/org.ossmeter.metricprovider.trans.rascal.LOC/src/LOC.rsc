module LOC

import analysis::m3::Core;
import analysis::m3::AST;
import analysis::graphs::Graph;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

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


@metric{locPerLanguage}
@doc{physical lines of code per language}
@friendlyName{Physical lines of code per language}
@appliesTo{generic()}
@uses{("genericLOC" :"genericLoc")}
map[str, int] locPerLanguage(rel[Language, loc, AST] asts = {}, map[loc, int] genericLoc = ()) {
  map[str, int] result = ();
  for (<l, f, a> <- asts, l != generic()) {
    result["<l>"]?0 += genericLoc[f];
  }
  return result;
}


@metric{mainLanguage}
@doc{Main development language of the project}
@friendlyName{Main development language of the project}
@appliesTo{generic()}
@uses{("locPerLanguage" :"locPerLanguage")}
Factoid mainLanguage(map[str, int] locPerLanguage = ()) {
  if (isEmpty(locPerLanguage)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  // generic() is already removed in locPerLanguage
  lrel[str, int] sorted = sort(toRel(locPerLanguage),
    bool (tuple[str, int] a, tuple[str, int] b) {
    	return a[1] < b[1];
    });

  mainLang = sorted[0];

  txt = "The main development language of the project is <mainLang[0]>, with <mainLang[1]> physical lines of code.";
  if (size(sorted) > 1) {
    otherTxt = intercalate(", ", ["<l[0]> (<l[1]>)" | l <- sorted[1..]]);
  
    txt += " Other used languages are <otherTxt>.";
  } 

  return factoid(txt, \four()); // star rating by language level? weighted by LOC? // http://www.cs.bsu.edu/homepages/dmz/cs697/langtbl.htm	
}

