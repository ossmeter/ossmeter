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
	return size(abstractTypes) / toReal(size(allTypes - abstractTypes));
}


@doc{
	Efferent Coupling
}
real Ce() {
	return 0.0;
}

@doc{
	Afferent Coupling
}
real Ca() {
	return 0.0;
}

@doc{
	Instability
}
real I(real Ca, real Ce) {
	divisor = Ca + Ce;
	if (divisor > 0) {
		return Ce / divisor;
	}
	return -1.0;
}


@doc{
	Calculate type dependencies (can be used for CBO and CF)
}
public rel[loc, loc] typeDependencies( 
  rel[loc subtype, loc supertype] superTypes,
  rel[loc caller, loc callee] methodCalls,
  rel[loc method, loc attribute] attributeAccesses,
  rel[loc object, loc \type] objectTypes, // variables, fields, parameters, etc.
  rel[loc \type, loc object] typeMembers, // variables, fields, parameters, methods
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
	
	return numDependencies / toReal(numPossibleDependencies);
}


