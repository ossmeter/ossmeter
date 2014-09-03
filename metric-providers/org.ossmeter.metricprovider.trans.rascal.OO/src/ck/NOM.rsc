module ck::NOM

int NOM(M3 model) = (0 | it + 1 | entity <- model@declarations<0>, isMethod(entity));
                                  
map[loc class, int methodCount] NOMperClass(M3 model) {
  map[loc, int] result = ();
  for (<class, method> <- model@containment, canContainMethods(class), isMethod(method)) {
    result[class] ? 0 += 1;
  }
  return result;
}

// Generic method for all number of methods related metrics
public map[loc, int] NOM(M3 m, set[Modifier] modifiers = {}) {
  classMethods = declaredMethods(m, checkModifiers = modifiers);
  classMethodsMap = toMap(classMethods);
  return (class : size(classMethodsMap[class]) | class <- classMethodsMap);
}
