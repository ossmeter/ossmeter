module PHPMetrics

extend lang::php::m3::Core;
import lang::php::m3::Uses;
import lang::php::m3::Declarations;
import lang::php::m3::Calls;

import util::Math;
import Set;

import PHP;
import DynamicFeatures;
import Includes;


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
public int getNumberOfDynamicFeatureUses(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + sumCounts(counts[f]) | f <- counts); // includes eval() calls 
}


@metric{numEvals}
@doc{number of calls to eval}
@friendlyName{numEvals}
@appliesTo{php()}
public int getNumberOfEvalCalls(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + counts[f][eval()] | f <- counts); 
}


@metric{numFunctionsWithDynamicFeatures}
@doc{Number of functions using at least one dynamic language feature}
@friendlyName{numFunctionsWithDynamicFeatures}
@appliesTo{php()}
public int getNumberOfFunctionsWithDynamicFeatures(rel[Language, loc, AST] asts = {})
{
	counts = getDynamicFeatureCountsPerFunction(asts);
	
	return ( 0 | it + 1 | f <- counts, sumCounts(counts[f]) > 0); 
}


@metric{IncludesResolutionHistogram}
@doc{Histogram counting number of times an include could be resolved to a certain number of files}
@friendlyName{IncludesResolutionHistogram}
@appliesTo{php()}
public map[int, int] getIncludesResolutionHistogram(rel[Language, loc, AST] asts = {})
{
	systems = { <root, sys> | <php(), root, phpSystem(sys)> <- asts };
	roots = domain(systems); 

	map[int, int] result = ();
	
	for (<root, sys> <- systems) {
		h = includeResolutionHistogram(sys, root, libs = { *domain(s) | <r, s> <- systems, r != root } );

		for (c <- h) {
			result[c]?0 += h[c];
		}
	}
	
	return result;
}


@metric{MissingLibrariesPHP}
@doc{Estimation of missing PHP libraries of the project}
@friendlyName{MissingLibrariesPHP}
@appliesTo{php()}
public list[str] estimateMissingLibraries(rel[Language, loc, AST] asts = {})
{
	systems = { <root, sys> | <php(), root, phpSystem(sys)> <- asts };
	roots = domain(systems);

	set[str] result = {};
	
	for (<root, sys> <- systems) {
		result += estimateMissingLibraries(sys, root, libs = { *domain(s) | <r, s> <- systems, r != root } );
	}
	
	return toList(result);
}
