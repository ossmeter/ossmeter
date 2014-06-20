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

import org::ossmeter::metricprovider::ProjectDelta;


@metric{WMC}
@doc{Compute your WMC}
@friendlyName{Weighted Method Count}
@appliesTo{java()}
map[loc class, num wmcCount] getWMC(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {},
	rel[Language, loc, AST] asts = {})
{
	map[loc class, num wmcCount] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[java(), file], ast <- asts[java(), file]) {
		result += (cl : sum([getCC(m, ast) | m <- m3@containment[cl], isMethod(m)]) | <cl, _> <- m3@containment, isClass(cl));
	}
	 
	return result;
}

@metric{CC}
@doc{Compute your McCabe}
@friendlyName{McCabe's Cyclomatic Complexity Metric}
@appliesTo{java()}
map[loc method, int cc] getCC(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {},
	rel[Language, loc, AST] asts = {})
{
	map[loc method, int cc] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);

	for (file <- changed, m3 <- m3s[java(), file], ast <- asts[java(), file]) {
		result += (m : getCC(m, ast) | <cl, m> <- m3@containment, isClass(cl), isMethod(m));
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

//@metric{ccovermethods}
@doc{Calculates the gini coefficient of cc over methods}
@friendlyName{ccovermethods}
@appliesTo{java()}
real giniCCOverMethods(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {},
	rel[Language, loc, AST] asts = {})
{
  map[loc, int] ccMap = getCC(delta=delta, workingCopyFolders=workingCopyFolders, m3s=m3s, asts=asts);
  
  distCCOverMethods = distribution(ccMap);
  
  if (size(distCCOverMethods) < 2) {
    return -1.0;
  }
  
  return gini([<0,0>]+[<x, distCCOverMethods[x]> | x <- distCCOverMethods]);
}