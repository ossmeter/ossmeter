module LOC

import lang::java::m3::Core;
import analysis::graphs::Graph;
import IO;
import List;
import String;
import Set;
import ValueIO;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

alias locResult = tuple[int total, int comment, int empty, int source];

@metric{loc}
@doc{loc}
@friendlyName{loc}
map[str, int] countLoc(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str file, int lines] result = ();
  map[str, list[str]] changedItemsPerRepo = getChangedItemsPerRepository(delta);
  
  for (str repo <- changedItemsPerRepo) {
    list[str] changedItems = changedItemsPerRepo[repo];
    loc workingCopyFolder = workingCopyFolders[repo];
    loc scratchFolder = scratchFolders[repo];
    
    for (str changedItem <- changedItems) {
      if (exists(workingCopyFolder+changedItem)) {
        loc scratchFile = scratchFolder+changedItem;
        M3 itemM3 = readBinaryValueFile(#M3, scratchFile[extension = scratchFile.extension+".m3"]);
        if ((unknownFileType(int i) := itemM3)) {
          result[changedItem] = i;
        } else {
          result[changedItem] = itemM3.total;
        }
      }
    }
  }
  
  return result;
}

@metric{locoverfiles}
@doc{locoverfiles}
@friendlyName{locoverfiles}
real giniLOCOverFiles(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str, int] locMap = countLoc(delta, workingCopyFolders, scratchFolders);
  
  distLOCOverMethods = distribution(locMap);
  
  return gini([<0,0>]+[<x, distLOCOverMethods[x]> | x <- distLOCOverMethods]);
}

@metric{locoverclass}
@doc{locoverclass}
@friendlyName{locoverclass}
real giniLOCOverClass(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str class, int lines] result = ();
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
          result += (lc.path : sc.end.line - sc.begin.line + 1 | <lc, sc> <- itemM3.model@declarations, isInterface(lc) || isClass(lc) || lc.scheme == "java+enum");
        }
      }
    }
  }
  
  distLOCOverClass = distribution(result);
  return gini([<0,0>]+[<x, distLOCOverClass[x]> | x <- distLOCOverClass]);
}