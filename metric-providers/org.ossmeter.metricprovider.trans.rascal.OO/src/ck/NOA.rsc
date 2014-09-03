module ck::NOA

int NOA(M3 model) = (0 | it + 1 | entity <- model@declarations<0>, isField(entity));
                                  
map[loc class, int methodCount] NOAperClass(M3 model) {
  map[loc, int] result = ();
  for (<class, field> <- model@containment, canContainMethods(class), isField(method)) {
    result[class] ? 0 += 1;
  }
  return result;
}

// Generic method for all number of methods related metrics
public map[loc, int] NOA(M3 m, set[Modifier] modifiers = {}) {
  classFields = declaredFields(m, checkModifiers = modifiers);
  classFieldsMap = toMap(classFields);
  return (class : size(classFieldsMap[class]) | class <- classFieldsMap);
}
