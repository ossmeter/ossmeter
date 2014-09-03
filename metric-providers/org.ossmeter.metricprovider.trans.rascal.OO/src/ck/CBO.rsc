module ck::CBO

public map[loc, int] CBO(M3 m) {
  set[loc] declaredClasses = classes(m@containment);
  int count = 0;
  map[loc, int] classCoupling = ();

  for (loc class <- declaredClasses) {
    // get a set of all elements in the class
    set[loc] classChildren = reach(m@containment, {class}); // (m@containment+)[class]
    // resolve the types/containment of all the children and the types they use
    set[loc] usedTypes = { usedType | usedType <- reach(m@typeDependency, classChildren), isClass(usedType) || isInterface(usedType) };
    usedTypes -= reach(m@typeInheritance, {class}) + class;
    classCoupling[class] = size(usedTypes);
  }
  
  return classCoupling;
}