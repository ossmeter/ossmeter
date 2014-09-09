module PHPMetrics

extend lang::php::m3::Core;
import lang::php::m3::Uses;
import lang::php::m3::Declarations;
import lang::php::stats::Stats;

import util::Math;

@metric{StaticNameResolutionHistogram}
@doc{Histogram counting type names that could be resolved to a certain number of declarations}
@friendlyName{StaticNameResolutionHistogram}
@appliesTo{php()}
map[int, int] getNameResolutionHistogram(rel[Language, loc, M3] m3s = {})
{
	models = { m | <php(), _, m> <- m3s };

	if (models == {})
	{
		return ();
	}

	M3 m3 = composeM3(|project:///|, models);

	m3@uses = { <l, n> | <l, n> <- m3@uses, n.scheme in ["php+class", "php+interface", "php+trait"] };

	m3 = addPredefinedDeclarations(m3);

	useDecl = resolveUsesToPossibleDeclarations(propagateAliasesInUses(m3));
	
	return calculateResolutionHistogram(countNumPossibleDeclarations(useDecl));
}


private set[str] varClassMetrics = {
						"class consts with variable class name",
						"object creation with variable class name",
						"calls of static methods with variable targets",
						"fetches of static properties with variable targets"};
		
private	set[str] varVarMetrics = {
						"assignments into variable-variables",
						"assignments w/ops into variable-variables",
						"list assignments into variable-variables",
						"ref assignments into variable-variables",
						"fetches of properties with variable names"};
						//"uses of variable-variables (including the above)",

private	set[str] varFuncMetrics = {
						"calls of variable function names",
						"calls of variable method names",
						"calls of static methods with variable names",
						"fetches of static properties with variable names"};

private set[str] varArgsMetrics = {"var-args support functions"};

private set[str] varIncludeMetrics = {"includes with non-literal paths"};

private set[str] overloadMetrics = {
						"definitions of overloads: set",
						"definitions of overloads: get",
						"definitions of overloads: isset",
						"definitions of overloads: unset",
						"definitions of overloads: call",
						"definitions of overloads: callStatic"};

private set[str] varLabelMetrics = {
						"break with non-literal argument",
						"continue with non-literal argument"};
						
private set[str] allDynamicMetrics = varClassMetrics + varVarMetrics + varFuncMetrics + varArgsMetrics + varIncludeMetrics
	+ overloadMetrics + varLabelMetrics;

@memo
private map[str, int] getCounts(rel[Language, loc, AST] asts)
{
	System sys = ( ast.file : ast.script  | <php(), _, ast> <- asts );
	return featureCounts(sys);
}

private int sumMetrics(rel[Language, loc, AST] asts, set[str] metricNames)
{
	counts = getCounts(asts);

	return toInt(sum([counts[n] | n <- metricNames]));
}


@metric{numDynamicFeatureUses-PHP}
@doc{Number of uses of dynamic PHP language features}
@friendlyName{Number of uses of dynamic PHP language features}
@appliesTo{php()}
public int getNumberOfDynamicFeatureUses(rel[Language, loc, AST] asts = {})
{
	 return sumMetrics(asts, allDynamicMetrics);
}


@metric{numEvals}
@doc{number of calls to eval}
@friendlyName{numEvals}
@appliesTo{php()}
public int getNumberOfEvalCalls(rel[Language, loc, AST] asts = {})
{
	return (0 | it + 1 | <php(), _, ast> <- asts, /call(name(name(/eval/i)), _) <- ast);
}
