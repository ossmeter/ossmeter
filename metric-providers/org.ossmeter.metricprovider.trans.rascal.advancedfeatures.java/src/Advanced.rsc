module Advanced

import lang::java::m3::Core;
import lang::java::m3::AST;

import org::ossmeter::metricprovider::MetricProvider;

private int countAdvancedFeatures(Declaration d) {
	result = 0; 

	visit (d) {
		// wildcards and union types
		case wildcard(): result += 1;
		case upperbound(_): result += 1;
		case lowerbound(_): result += 1;
		case unionType(_): result += 1;
		
		// anonymous classes
		case newObject(_, _, _, Declaration d): result += 1;
		case newObject(_, _, Declaration d): result += 1;
	}

	return result;
}


@metric{AdvancedLanguageFeaturesJava}
@doc{Usage of advanced Java features (wildcards, union types and anonymous classes)}
@friendlyName{Usage of advanced Java features}
@appliesTo{java()}
public map[loc file, int count] countUsesOfAdvancedLanguageFeatures(rel[Language, loc, AST] asts = {}) {
	map[loc, int] result = ();

	for (<java(), f, declaration(d)> <- asts) {
		result[f] = countAdvancedFeatures(d);
	}

	return result;
}

@metric{AdvancedLanguageFeaturesJavaQuantiles}
@doc{Quantiles of counts of advanced Java features (wildcards, union types and anonymous classes)}
@friendlyName{Usage of advanced Java features quantiles}
@appliesTo{java()}
@historic
@uses{("AdvancedLanguageFeaturesJava": "v")}
public map[str, real] countUsesOfAdvancedLanguageFeaturesQ(map[loc, int] v = ()) {
	return quantiles(v);
}

