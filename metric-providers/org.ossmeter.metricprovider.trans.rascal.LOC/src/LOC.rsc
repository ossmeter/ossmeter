module LOC

import analysis::m3::Core;
import analysis::m3::AST;
import analysis::graphs::Graph;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

import Prelude;

import Generic;
 
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
  set[loc] filesWithLanguageDetected = {};
  
  // first count LOC of files with extracted ASTs
  for (<l, f, a> <- asts, l != generic()) {
    result["<l>"]?0 += genericLoc[f]?0;
    filesWithLanguageDetected += {f};
  }
  
  // then guess languages of other files by their extension
  for (<l, f, a> <- asts, f notin filesWithLanguageDetected) {
  	lang = estimateLanguageByFileExtension(f);
  	if (lang != "") {
  	  result[lang]?0 += genericLoc[f]?0;
  	}
  }
  
  return result;
}


@metric{codeSize}
@doc{The size of the project's code base}
@friendlyName{Code Size}
@appliesTo{generic()}
@uses{("locPerLanguage": "locPerLanguage",
       "org.ossmeter.metricprovider.trans.rascal.activecommitters.projectAge": "projectAge",
       "org.ossmeter.metricprovider.trans.rascal.activecommitters.numberOfActiveCommittersLongTerm": "numberOfActiveCommittersLongTerm")}
Factoid codeSize(
	map[str, int] locPerLanguage = (),
	int projectAge = -1,
	int numberOfActiveCommittersLongTerm = -1
) {
  if (isEmpty(locPerLanguage)) {
    throw undefined("No LOC data available", |unknown:///|);
  }

  // generic() is already removed in locPerLanguage
  lrel[str, int] sorted = sort(toRel(locPerLanguage),
    bool (tuple[str, int] a, tuple[str, int] b) {
    	return a[1] > b[1]; // sort from high to low
    });

  totalSize = ( 0 | it + locPerLanguage[l] | l <- locPerLanguage );

  mainLang = sorted[0];

  txt = "The total size of the code base is <totalSize> physical lines of code. The main development language of the project is <mainLang[0]>, with <mainLang[1]> physical lines of code.";
  if (size(sorted) > 1) {
    otherTxt = intercalate(", ", ["<l[0]> (<l[1]>)" | l <- sorted[1..]]);
  
    txt += " The following <size(sorted) - 1> other languages were recognized: <otherTxt>.";
  }
  
  if (projectAge > 0) {
  	txt += " The age of the code base is <projectAge> days.";
  }

  if (numberOfActiveCommittersLongTerm > 0) {
    txt += " In the last six months there have been <numberOfActiveCommittersLongTerm> people working on this project.";
  }

  stars = 1;
  
  if (totalSize < 1000000) { // 1 star if LOC > 1M
    stars += 1;
    
    if (totalSize < 500000) {
      stars += 1;
    }
  
    if (size(locPerLanguage) <= 2) {
      stars += 1;
    }
  }
  
  return factoid(txt, starLookup[stars]); // star rating by language level? weighted by LOC? // http://www.cs.bsu.edu/homepages/dmz/cs697/langtbl.htm	
}
