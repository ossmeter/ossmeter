module OO

import Prelude;
import util::Math;
import analysis::graphs::Graph;

import analysis::m3::Core;
import analysis::m3::AST;

import Generic;



@doc{
	Reuse Ratio (No. of superclasses / total no. of classes)
}
real RR(rel[loc, loc] superTypes, set[loc] allTypes) {
	if (size(allTypes) > 0) {
		return size(range(superTypes)) / toReal(size(allTypes));
	}
	return 0.0;
}


@doc{
	Specialization Ratio (No. of subclasses/ no. of super classes)
}
real SR(rel[loc, loc] superTypes) {
	nrOfSubTypes = size(domain(superTypes));
	nrOfSuperTypes = size(range(superTypes));

	if (nrOfSuperTypes > 0) {
		return nrOfSubTypes / toReal(nrOfSuperTypes);
	}
	return 0.0;
}

@doc{
	Abstractness (nr of abstract types / nr of concrete types)
}
real A(set[loc] abstractTypes, set[loc] allTypes) {
	numConcreteTypes = size(allTypes - abstractTypes);
	if (numConcreteTypes > 0) {
		return size(abstractTypes) / toReal(numConcreteTypes);
	}
	return 0.0;
}


@doc{
	Efferent Coupling
}
map[loc, int] Ce(rel[loc package, loc \type] packageTypes, rel[loc depender, loc dependee] typeDependencies) {
	packages = domain(packageTypes);
	
	otherPackageDependencies = typeDependencies o invert(packageTypes) - invert(packageTypes);
	
	return ( p : ( 0 | it + 1 | t <- packageTypes[p], otherPackageDependencies[t] != {} ) | p <- packages ); 
}

@doc{
	Afferent Coupling
}
map[loc, int] Ca(rel[loc package, loc \type] packageTypes, rel[loc depender, loc dependee] typeDependencies) {
	otherPackageDependencies = typeDependencies o invert(packageTypes) - invert(packageTypes);

	typesDependingOnPackage = packageTypes o invert(typeDependencies);	

	return ( p : size(typesDependingOnPackage[p]) | p <- domain(packageTypes) );
}

@doc{
	Instability
}
real I(int Ca, int Ce) {
	divisor = Ca + Ce;
	if (divisor > 0) {
		return Ce / toReal(divisor);
	}
	return 0.0;
}


@doc{
	Calculate type dependencies (can be used for CBO and CF)
}
public rel[loc, loc] typeDependencies( 
  rel[loc subtype, loc supertype] superTypes,
  rel[loc caller, loc callee] methodCalls,
  rel[loc method, loc attribute] attributeAccesses,
  rel[loc object, loc \type] objectTypes, // variables, fields, parameters, exceptions, etc.
  rel[loc \type, loc object] typeMembers, // variables, fields, parameters, exceptions, methods, inner classes
  set[loc] allTypes) {
  
  dependencies = typeMembers o (methodCalls + attributeAccesses) o invert(typeMembers); // uses of members of other types
  dependencies += typeMembers o objectTypes;     // include types of variables, fields, parameters, etc
  dependencies += rangeR(typeMembers, allTypes); // include inner classes etc.
  dependencies += superTypes+; // include ancestor types
  
  return dependencies;
}


@doc{
	Coupling Factor
}
public real CF(rel[loc, loc] typeDependencies, rel[loc, loc] superTypes, set[loc] allTypes) {
	numTypes = size(allTypes);
	
	numDependencies = size(typeDependencies + invert(typeDependencies));
	
	numPossibleDependencies = (numTypes * (numTypes - 1) - 2 * size(superTypes+)); // excluding inheritance

	if (numPossibleDependencies > 0) {
		return numDependencies / toReal(numPossibleDependencies);
	}
	else {
		return 0.0;
	}
}


@doc{
	Tight Class Cohesion per type
}
map[loc, real] TCC(
	rel[loc \type, loc method] typeMethods,
 	rel[loc \type, loc field] typeFields,
 	rel[loc method1, loc method2] calls,
 	rel[loc method, loc field] fieldAccesses,
 	set[loc] allTypes) {

	map[loc, real] tcc = ();

	for (t <- allTypes) {
		methodsOfT = typeMethods[t];
		numMethods = size(methodsOfT);
		maxConnections = numMethods * (numMethods - 1);
		
		fieldConnections = rangeR(calls, methodsOfT)+ o rangeR(fieldAccesses, typeFields[t]); 
		methodConnections = fieldConnections o invert(fieldConnections);
		
		directConnections = methodConnections - ident(methodsOfT);

		if (maxConnections > 0) {
			tcc[t] = size(directConnections) / toReal(maxConnections);
		} else {
			tcc[t] = -1.0;
		}
	}
	
	return tcc;
}


@doc{
	Loose Class Cohesion per type
}
map[loc, real] LCC(
	rel[loc \type, loc method] typeMethods,
 	rel[loc \type, loc field] typeFields,
 	rel[loc method1, loc method2] calls,
 	rel[loc method, loc field] fieldAccesses,
 	set[loc] allTypes) {

	map[loc, real] lcc = ();

	for (t <- allTypes) {
		methodsOfT = typeMethods[t];
		numMethods = size(methodsOfT);
		maxConnections = numMethods * (numMethods - 1);
		
		fieldConnections = rangeR(calls, methodsOfT)+ o rangeR(fieldAccesses, typeFields[t]); 
		methodConnections = fieldConnections o invert(fieldConnections);
		
		indirectConnections = (methodConnections+) - ident(methodsOfT);
		
		if (maxConnections > 0) {
			lcc[t] = size(indirectConnections) / toReal(maxConnections);
		} else {
			lcc[t] = -1.0;
		}
	}
	
	return lcc;
}

@doc{
	LCOM4: Lack of Cohesion of Methods as defined by Hitz & Montazeri
	The number of connected components within the related methods graph of a class.
	Two methods are related if one of them calls the other of if they both access the same field.
}
map[loc, int] LCOM4(
	rel[loc caller, loc callee] methodCalls,
	rel[loc method, loc field] fieldAccesses,
	rel[loc \type, loc method] methods,
	rel[loc \type, loc field] fields,
	set[loc] allTypes) {
	
	map[loc, int] lcom = ();
	
	for (t <- allTypes) {
		fs = fields[t];
		ms = methods[t];
		
		localAccesses = rangeR(domainR(fieldAccesses, ms), fs);
		
		relatedMethods = carrierR(methodCalls, ms);
		relatedMethods += localAccesses o invert(localAccesses);
		
		lcom[t] = size(connectedComponents(relatedMethods));
	}
	
	return lcom;
}
