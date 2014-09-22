module WMC

import lang::php::m3::AST;
import lang::php::m3::Core;
import Prelude;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;
import util::Math;

import lang::php::ast::AbstractSyntax;


@metric{WMCPHP}
@doc{Weighted Method Count for PHP classes}
@friendlyName{Weighted Method Count for PHP classes}
@appliesTo{php()}
@uses = ("CCPHP" : "methodCC")
map[loc class, num wmcCount] getWMC(rel[Language, loc, M3] m3s = {}, map[loc, int] methodCC = ())
{
	map[loc class, num wmcCount] result = ();
	
	for (<php(), _, m3> <- m3s) {
		result += (cl : sum([methodCC[m]?0 | m <- m3@containment[cl], isMethod(m)]) | <cl, _> <- m3@containment, isClass(cl));
	}
	 
	return result;
}

@metric{CCPHP}
@doc{McCabe's Cyclomatic Complexity for PHP methods}
@friendlyName{McCabe's Cyclomatic Complexity for PHP methods}
@appliesTo{php()}
map[loc, int] getCC(rel[Language, loc, AST] asts = {}) 
{
  map[loc method, int cc] result = ();

  for (<php(), _, phpAST(a)> <- asts) {
    result += ( m@decl : 1 + countCC(body) | /m:method(_, _, _, _, body) <- a );
  }
  
  return result;
}

int countCC(list[Stmt] stats) {
  int count = 0;
  
  for (s <- stats) {
    switch(s) {
      case \break(_): count += 1;
      case \continue(_): count += 1;
      case declare(_, body): count += countCC(body);
      case do(_, body): count += 1 + countCC(body);
      case \for(_, _, _, body): count += 1 + countCC(body);
      case foreach(_, _, _, _, body): count += 1 + countCC(body);
      case goto(_): count += 1;
      case \while(_, body): count += 1 + countCC(body);
      case block(body): count += countCC(body);
      
      case \if(_, body, elseIfs, elseClause):
        count += 1 + countCC(body) + 
          toInt(sum([ 1 + countCC(b) | elseIf(_, b) <- elseIfs ])) + 
          ((\else(b) := elseClause) ? countCC(b) : 0);
      
      case \switch(_, cases):
         count += toInt(sum([ 1 + countCC(body) | \case(_, body) <- cases]));
      
      case tryCatch(body, catches):
        count += countCC(body) +
          toInt(sum([1 + countCC(b) | \catch(_, _, b) <- catches])); 
        
      case tryCatchFinally(body, catches, finallyBody):
        count += countCC(body) + countCC(finallyBody) +
          toInt(sum([1 + countCC(b) | \catch(_, _, b) <- catches]));
    }    
  }
  return count;
}

@metric{CCOverPHPMethods}
@doc{Calculates the gini coefficient of CC over PHP methods}
@friendlyName{The gini coefficient of CC over PHP methods}
@appliesTo{php()}
@uses = ("CCPHP" : "methodCC")
real giniCCOverMethods(map[loc, int] methodCC = ()) {
  if (isEmpty(methodCC)) {
    return -1.0;
  }
  
  distCCOverMethods = distribution(methodCC);
  
  if (size(distCCOverMethods) < 2) {
    return -1.0;
  }
  
  return gini([<0,0>]+[<x, distCCOverMethods[x]> | x <- distCCOverMethods]);
}