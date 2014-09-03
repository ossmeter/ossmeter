module ck::RFC

public map[loc, int] RFC(M3 m) {
  map[loc, int] RFC = ();
  
  set[loc] declaredClasses = classes(m@containment);
  
  for (class <- declaredClasses) {
    set[loc] classChildren = reach(m@containment, {class});
    RFC[class] = size({ accessedMethod | loc accessedMethod <- range(domainR(m@methodInvocation, classChildren)) + classChildren, isMethod(accessedMethod) });
  }
  
  return RFC;
}