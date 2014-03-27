module NOM

import lang::java::m3::Core;
import List;
import String;
import Map;
import ValueIO;
import IO;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

int numberOfMethods(loc cl, M3 model) = size([ m | m <- model@containment[cl], isMethod(m)]);

map[str class, int nom] getNOM(M3 fileM3) {
  return (replaceAll(replaceFirst(cl.path, "/", ""), "/", "."):numberOfMethods(cl, fileM3.model) | <cl,_> <- fileM3.model@containment, isClass(cl));
}

map[str class, int nom] getNOM(unknownFileType(int lines)) {
  return ("": -1);
}

@metric{NOM}
@doc{Compute your NOM}
@friendlyName{Number of methods}
map[str class, int nom] NOM(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str class, int nom] result = ();
  map[str, list[str]] changedItemsPerRepo = getChangedItemsPerRepository(delta);
  
  for (str repo <- changedItemsPerRepo) {
    list[str] changedItems = changedItemsPerRepo[repo];
    loc workingCopyFolder = workingCopyFolders[repo];
    loc scratchFolder = scratchFolders[repo];
    
    for (str changedItem <- changedItems) {
      if (exists(workingCopyFolder+changedItem)) {
        loc scratchFile = scratchFolder+changedItem;
        M3 itemM3 = readBinaryValueFile(#M3, scratchFile[extension = scratchFile.extension+".m3"]);
        if (!(unknownFileType(_) := itemM3)) {
          result += (replaceAll(replaceFirst(cl.path, "/", ""), "/", "."):numberOfMethods(cl, itemM3.model) | <cl,_> <- itemM3.model@containment, isClass(cl));
        }
      }
    }
  }
  
  return result;
}