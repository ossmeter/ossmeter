module NOM

import lang::java::m3::Core;
import List;
import String;
import Map;

int numberOfMethods(loc cl, M3 model) = size([ m | m <- model@containment[cl], isMethod(m)]);

map[str class, int nom] getNOM(M3 fileM3) {
  return (replaceAll(replaceFirst(cl.path, "/", ""), "/", "."):numberOfMethods(cl, fileM3) | <cl,_> <- fileM3@containment, isClass(cl));
}

map[str class, int nom] getNOM(loc file) {
  return ("": -1);
}