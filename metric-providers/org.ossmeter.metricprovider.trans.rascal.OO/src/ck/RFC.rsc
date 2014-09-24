module ck::RFC

import Set;

@doc{
	Response for class (nr of methods reachable by class methods (direct and indirect))
}
public map[loc, int] RFC(rel[loc, loc] calls, rel[loc, loc] typeMethods, set[loc] allTypes) {
	callsTrans = calls+;

	return ( t : size(callsTrans[typeMethods[t]] - typeMethods[t]) | t <- allTypes );
}


/*
public map[loc, int] RFC(M3 m) {
  map[loc, int] RFC = ();
  
  set[loc] declaredClasses = classes(m@containment);
  
  for (class <- declaredClasses) {
    set[loc] classChildren = reach(m@containment, {class});
    RFC[class] = size({ accessedMethod | loc accessedMethod <- range(domainR(m@methodInvocation, classChildren)) + classChildren, isMethod(accessedMethod) });
  }
  
  return RFC;
}
*/