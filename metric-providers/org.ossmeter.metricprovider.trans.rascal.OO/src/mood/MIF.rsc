module mood::MIF

import Set;
import util::Math;

@doc{
	Method Inheritance Factor (can also be used for Attribute Inheritance Factor)	
}
public map[loc, real] MIF(
	rel[loc \type, loc method] typeMethods,
	rel[loc \type, loc method] inheritableConcreteMethods,
	rel[loc subtype, loc supertype] superTypes,
	set[loc] allTypes) {
	
	map[loc, real] mif = ();
	
	ancestors = superTypes+;
	
	for (t <- allTypes) {
		newMethods = size(typeMethods[t]);
		inheritedMethods = size(inheritableConcreteMethods[ancestors[t]]);
		totalMethods = newMethods + inheritedMethods;
		
		if (totalMethods > 0) {
			mif[t] = inheritedMethods / toReal(totalMethods);
		} else {
			mif[t] = -1.0;
		}
	}
	
	return mif;
}
