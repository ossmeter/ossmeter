module mood::PF

import util::Math;
import Set;

@doc{
	Polymorphism Factor (nr of overrides / nr of overrides possible)
}
public real PF(rel[loc subtype, loc supertype] superTypes,
				rel[loc submethod, loc supermethod] overrides,
				rel[loc \type, loc method] overridableMethods,
				set[loc] allTypes) {

	ancestors = superTypes+;

	numPossibleOverrides = ( 0 | it + size(overridableMethods[ancestors[c]]) | c <- allTypes );
	
	if (numPossibleOverrides > 0) {
		return size(overrides) / toReal(numPossibleOverrides);
	}
	
	return 0.0;
}