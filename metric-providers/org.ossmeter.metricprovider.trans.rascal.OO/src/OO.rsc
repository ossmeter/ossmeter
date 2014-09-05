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





