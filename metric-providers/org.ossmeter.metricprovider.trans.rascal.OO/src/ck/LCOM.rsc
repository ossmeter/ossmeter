module ck::LCOM

import Set;
import List;

@doc{
	Lack of Cohesion of Methods per class
}
map[loc, int] LCOM(
	rel[loc method, loc field] fieldAccesses,
	rel[loc \type, loc method] methods,
	rel[loc \type, loc field] fields,
	set[loc] allTypes) {
	
	map[loc, int] lcom = ();
	
	for (t <- allTypes) {
		fs = fields[t];
		ms = toList(methods[t]);
		
		P = 0;
		Q = 0;
		
		for (i <- [0..size(ms)]) {
			for (j <- [i+1 .. size(ms)]) {
				if ((fieldAccesses[ms[i]] & fieldAccesses[ms[j]] & fs) == {}) {
					P += 1;
				} else {
					Q += 1;
				}
			}		
		}
		
		lcom[t] = (P > Q) ? (P - Q) : 0;
	}
	
	return lcom;
}

