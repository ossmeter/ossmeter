module PHPMetrics

extend lang::php::m3::Core;
import lang::php::m3::Uses;
import lang::php::m3::Declarations;
import lang::php::m3::Calls;

import util::Math;
import Map;
import Set;
import List;

import PHP;
import DynamicFeatures;
import Includes;

import org::ossmeter::metricprovider::MetricProvider;

@memo
private M3 systemM3WithStdLib(rel[Language, loc, M3] m3s) {
	return addPredefinedDeclarations(systemM3(m3s));
}


@metric{StaticTypeNameResolutionHistogram}
@doc{Histogram counting type names that could be resolved to a certain number of declarations}
@friendlyName{StaticTypeNameResolutionHistogram}
@appliesTo{php()}
map[int, int] getTypeNameResolutionHistogram(rel[Language, loc, M3] m3s = {})
{
	M3 m3 = systemM3WithStdLib(m3s);

	m3@uses = { <l, n> | <l, n> <- m3@uses, n.scheme in ["php+class", "php+interface", "php+trait"] };

	useDecl = resolveUsesToPossibleDeclarations(m3);
	
	return calculateResolutionHistogram(countNumPossibleDeclarations(useDecl));
}


@metric{StaticMethodNameResolutionHistogram}
@doc{Histogram counting called method names that could be resolved to a certain number of declarations}
@friendlyName{StaticMethodNameResolutionHistogram}
@appliesTo{php()}
map[int, int] getMethodNameResolutionHistogram(rel[Language, loc, M3] m3s = {})
{
	M3 m3 = composeM3s(m3s); // m3 before resolution

	calls = resolveMethodCallsAndFieldAccesses(m3)[1];

	return memberResolutionHistogram(calls, m3);
}


@metric{StaticFieldNameResolutionHistogram}
@doc{Histogram counting accessed field names that could be resolved to a certain number of declarations}
@friendlyName{StaticFieldNameResolutionHistogram}
@appliesTo{php()}
map[int, int] getFieldNameResolutionHistogram(rel[Language, loc, M3] m3s = {})
{
	M3 m3 = composeM3s(m3s); // m3 before resolution

	accesses = resolveMethodCallsAndFieldAccesses(m3)[2];

	return memberResolutionHistogram(accesses, m3);
}


@memo
map[loc, map[DynamicFeature, int]] getDynamicFeatureCountsPerFunction(rel[Language, loc, AST] asts) {
	map[loc, map[DynamicFeature, int]] result = ();
	
	scripts = { s | <php(), _, phpAST(s)> <- asts };

	if (scripts == {}) {
		throw undefined("No PHP ASTs available.", |tmp:///|);
	}

	top-down-break visit (scripts) {
		case m:method(_, _, _, _, _): result[m@decl] = getDynamicFeatureCounts(m);
		case f:function(_, _, _, _): result[f@decl] = getDynamicFeatureCounts(f);
	}
	
	return result;
}


@metric{numDynamicFeatureUses-PHP}
@doc{Number of uses of dynamic PHP language features}
@friendlyName{Number of uses of dynamic PHP language features}
@appliesTo{php()}
@historic
public int getNumberOfDynamicFeatureUses(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + sumCounts(counts[f]) | f <- counts); // includes eval() calls 
}


@metric{numEvals}
@doc{number of calls to eval}
@friendlyName{numEvals}
@appliesTo{php()}
@historic
public int getNumberOfEvalCalls(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + counts[f][eval()] | f <- counts); 
}


@metric{numFunctionsWithDynamicFeatures}
@doc{Number of functions using at least one dynamic language feature}
@friendlyName{numFunctionsWithDynamicFeatures}
@appliesTo{php()}
@historic
public int getNumberOfFunctionsWithDynamicFeatures(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + 1 | f <- counts, sumCounts(counts[f]) > 0); 
}


@metric{IncludesResolutionHistogram}
@doc{Histogram counting number of times a PHP include could be resolved to a certain number of files}
@friendlyName{IncludesResolutionHistogram}
@appliesTo{php()}
public map[int, int] getIncludesResolutionHistogram(rel[Language, loc, AST] asts = {})
{
	systems = { <root, sys> | <php(), root, phpSystem(sys)> <- asts };

	return includeResolutionHistogram(systems);
}


@metric{MissingLibrariesPHP}
@doc{Estimation of missing PHP libraries of the project}
@friendlyName{MissingLibrariesPHP}
@appliesTo{php()}
public set[str] estimateMissingLibraries(rel[Language, loc, AST] asts = {})
{
	systems = { <root, sys> | <php(), root, phpSystem(sys)> <- asts };

	return estimateMissingLibraries(systems);
}


@metric{DynamicLanguageFeaturesPHP}
@doc{The use of dynamic language features in PHP code.}
@friendlyName{DynamicLanguageFeaturesPHP}
@appliesTo{php()}
@uses=("numFunctionsWithDynamicFeatures": "numFunctionsWithDynamicFeatures")
Factoid dynamicLanguageFeaturesFactoid(rel[Language, loc, AST] asts = {}, int numFunctionsWithDynamicFeatures = -1) {
  numFunctions = size(getDynamicFeatureCountsPerFunction(asts));

  if (numFunctionsWithDynamicFeatures == -1 || numFunctions == 0) {
    throw undefined("No data available.", |tmp:///|);
  }

  perc = percent(numFunctionsWithDynamicFeatures, numFunctions);
  
  stars = \one();
  txt = "The use of dynamic language features in PHP code negatively influences its static analysability.";
  
  if (perc <= 5) {
    stars = four();
    txt += "In this project, only <perc>% of the functions/methods use dynamic language features."; 
  }
  else if (perc <= 10) {
    stars = three();
    txt += "In this project, <perc>% of the functions/methods use dynamic language features."; 
  }
  else if (perc <= 15) {
    stars = two();
    txt += "In this project, <perc>% of the functions/methods use dynamic language features."; 
  }
  else {
    txt += "In this project, <perc>% of the functions/methods use dynamic language features."; 
  }

  return factoid(txt, stars);
}


@metric{StaticNameResolutionPHP}
@doc{How well could the names in the PHP code be statically resolved?}
@friendlyName{StaticNameResolutionPHP}
@appliesTo{php()}
@uses=(
  "StaticTypeNameResolutionHistogram": "typeNames",
  "StaticMethodNameResolutionHistogram": "methodNames",
  "StaticFieldNameResolutionHistogram": "fieldNames",
  "IncludesResolutionHistogram": "includes",
  "MissingLibrariesPHP": "missingLibraries"
)
Factoid staticNameResolutionFactoid(
  map[int, int] typeNames = (),
  map[int, int] methodNames = (),
  map[int, int] fieldNames = (),
  map[int, int] includes = (),
  set[str] missingLibraries = {}
) {
  if (typeNames + methodNames + fieldNames + includes == ()) {
    throw undefined("No name resolution data available.", |tmp:///|);
  }

  totalNames = 0;
  totalUnresolved = 0;
  totalAmbiguous = 0;
  
  txt = "The percentages of unresolved and ambiguous names for the following categories are respectively:\n";
  
  for (<m, n> <- [<typeNames, "Type names">, <methodNames, "Method names">, <fieldNames, "Field names">, <includes, "Included files">]) {
    if (m == ()) {
      continue;
    }
  
    numNames = sum([m[i] | i <- m]);
    unresolved = m[0]?0;
    ambiguous = numNames - (m[0]?0) - (m[1]?0);
  
    totalNames += numNames;
    totalUnresolved += unresolved;
    totalAmbiguous += ambiguous;
    
    txt += "<n>: <percent(unresolved, numNames)>% <percent(ambiguous, numNames)>%\n";
  }

  if (missingLibraries != {}) {
    txt += "The following missing libraries were detected: <intercalate(", ", sort(toList(missingLibraries)))>. Adding these to the configuration might increase the name resolution scores.";
  }
  
  percProblematic = percent(totalUnresolved + totalAmbiguous, totalNames);
  
  stars = \one();
  
  if (totalUnresolved + totalAmbiguous == 0) {
    stars = four();
    txt = "All names in the code could be statically resolved to a unique declaration.\n";
  }
  else {
    txt = "<percProblematic>% of the names in the code could not be statically resolved to a unique declaration. This might influence the results of other metrics, for instance the OO related ones.\n" + txt;
  
    if (percProblematic < 10) {
      stars = four();
      txt = "Only <txt>"; 
    }
    else if (percProblematic < 20) {
      stars = three();
    }
    else if (percProblematic < 30) {
      stars = two();
    }
  }

  return factoid(txt, stars);
}

