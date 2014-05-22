module NOA

import lang::java::m3::Core;
import List;
import String;
import Map;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import IO;
import ValueIO;

int numberOfAttributes(loc cl, M3 model) = size([ m | m <- model@containment[cl], isField(m)]);

map[str class, int nom] getNOA(M3 fileM3) {
  return (replaceAll(replaceFirst(cl.path, "/", ""), "/", "."):numberOfAttributes(cl, fileM3.model) | <cl,_> <- fileM3.model@containment, isClass(cl));
}

map[str class, int nom] getNOA(unknownFileType(int lines)) {
  return ("": -1);
}

@metric{NOA}
@doc{Compute your NOA}
@friendlyName{Number of attributes}
map[str class, int noa] NOA(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str class, int noa] result = ();
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
          result += (replaceAll(replaceFirst(cl.path, "/", ""), "/", "."):numberOfAttributes(cl, itemM3.model) | <cl,_> <- itemM3.model@containment, isClass(cl));
        }
      }
    }
  }
  
  return result;
}