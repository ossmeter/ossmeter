module WMC

import lang::java::m3::AST;
import lang::java::m3::Core;
import IO;
import Node;
import List;
import String;
import Map;
import ValueIO;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{WMC}
@doc{Compute your WMC}
@friendlyName{Weighted Method Count}
map[str class, num wmcCount] getWMC(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str class, num wmcCount] result = ();
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
          result += (replaceAll(replaceFirst(cl.path, "/", ""), "/", ".") : sum([getCC(m, itemM3.ast) | m <- itemM3.model@containment[cl], isMethod(m)]) | <cl, _> <- itemM3.model@containment, isClass(cl));
        }
      }
    }
  }
  
  return result;
}

map[str class, num wmcCount] getWMC(unknownFileType(int lines)) = ("": -1);
map[str class, int cc] getCC(unknownFileType(int lines)) = ("" : -1);

@metric{CC}
@doc{Compute your McCabe}
@friendlyName{McCabe's Cyclomatic Complexity Metric}
map[str method, int cc] getCC(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str method, int cc] result = ();
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
          result += (replaceAll(replaceFirst(m.path, "/", ""), "/", ".") : getCC(m, itemM3.ast) | <cl, _> <- itemM3.model@containment, isClass(cl), m <- itemM3.model@containment[cl], isMethod(m));
        }
      }
    }
  }
  
  return result;
}

Declaration getASTOfMethod(loc methodLoc, Declaration fileAST) {
  visit(fileAST) {
    case Declaration d: {
      if ("decl" in getAnnotations(d) && d@decl == methodLoc) {
        return d;
      }
    }
  }
  throw "ast not found for method: <methodLoc>";
}

int getCC(loc m, Declaration ast) {
  int count = 1;
  Declaration methodAST = getASTOfMethod(m, ast);
  
  visit(methodAST) {
    case \foreach(Declaration parameter, Expression collection, Statement body): count += 1;
    case \for(list[Expression] initializers, Expression condition, list[Expression] updaters, Statement body): count += 1;
    case \if(Expression condition, Statement thenBranch, Statement elseBranch): count += 1;
    case \for(list[Expression] initializers, list[Expression] updaters, Statement body): count += 1;
    case \if(Expression condition, Statement thenBranch): count += 1;
    case \case(Expression expression): count += 1;
    case \while(Expression condition, Statement body): count += 1;
    case \do(Statement body, Expression condition): count += 1;
    case \catch(Declaration exception, Statement body): count += 1;
    case \infix(Expression lhs, "||", Expression rhs, list[Expression] extendedOperands): count += 1 + size(extendedOperands);
    case \infix(Expression lhs, "&&", Expression rhs, list[Expression] extendedOperands): count += 1 + size(extendedOperands);
  }
  return count;
}

@metric{ccovermethods}
@doc{Calculates the gini coefficient of cc over methods}
@friendlyName{ccovermethods}
real giniCCOverMethods(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str, int] ccMap = getCC(delta, workingCopyFolders, scratchFolders);
  
  distCCOverMethods = distribution(ccMap);
  
  return gini([<0,0>]+[<x, distCCOverMethods[x]> | x <- distCCOverMethods]);
}