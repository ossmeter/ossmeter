module mood::MIF

import Set;
import IO;

/* 
 *  Method Inheritance Factor from MOOD Metrics Suite 
 */
 
num MIF(M3 model) {
  num numerator = 0.0;
  num denominator = 0.0;
  
  map[loc, set[loc]] declaredMethods = declaredMethodsperClass(model);
  rel[loc, loc] inheritedMethods = inheritedEntities(model);
  
  for (c <- model@declarations<0>, isClass(c)) {
    inheritedMethodsCount = size({ m | m <- inheritedMethods[c], isMethod(m) });
    numerator = numerator + inheritedMethodsCount;
    denominator = denominator + inheritedMethodsCount + (c in declaredMethods ? size(declaredMethods[c]) : 0);
  }
  
  return numerator/denominator;
}
