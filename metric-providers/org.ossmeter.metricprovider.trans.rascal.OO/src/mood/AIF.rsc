module mood::AIF

import Set;
import IO;

/* 
 *  Attribute Inheritance Factor from MOOD Metrics Suite 
 */
 
num AIF(M3 model) {
  num numerator = 0.0;
  num denominator = 0.0;
  
  map[loc, set[loc]] declaredFields = declaredFieldsperClass(model);
  rel[loc, loc] inheritedFields = inheritedEntities(model);
  
  for (c <- model@declarations<0>, isClass(c)) {
    inheritedFieldsCount = size({ m | m <- inheritedFields[c], isField(m) });
    numerator = numerator + inheritedFieldsCount;
    denominator = denominator + inheritedFieldsCount + (c in declaredFields ? size(declaredFields[c]) : 0);
  }
  
  return numerator/denominator;
}
