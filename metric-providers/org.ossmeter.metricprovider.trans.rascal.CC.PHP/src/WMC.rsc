module WMC

import lang::php::m3::AST;
import lang::php::m3::Core;
import lang::php::ast::AbstractSyntax;

import Prelude;
import List;
import util::Math;

import CC;
import org::ossmeter::metricprovider::Factoid;


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
    result += ( m@decl : countCC(body) | /m:method(_, _, _, _, body) <- a );
  }
  
  return result;
}

int countCC(list[Stmt] stats) {
  int count = 1;
  
  visit (stats) {
      case \do(_, body): count += 1;
      case \for(_, _, _, body): count += 1;
      case \foreach(_, _, _, _, body): count += 1;
      case \while(_, body): count += 1;
      
      case \if(_, _, elseIfs, _):
        count += 1 + size(elseIfs); 
      
      case \tryCatch(_, catches):
        count += size(catches);
        
      case \tryCatchFinally(_, catches, _):
        count += size(catches);
  }

  return count;
}

@metric{CCOverPHPMethods}
@doc{Calculates the gini coefficient of CC over PHP methods}
@friendlyName{The gini coefficient of CC over PHP methods}
@appliesTo{php()}
@uses = ("CCPHP": "methodCC")
real giniCCOverMethodsPHP(map[loc, int] methodCC = ()) {
  return giniCCOverMethods(methodCC);
}


@metric{CCPHPFactoid}
@doc{The cyclometic complexity of the project's PHP code}
@friendlyName{CCPHPFactoid}
@appliesTo{php()}
@uses = ("CCPHP" : "methodCC")
Factoid CC(map[loc, int] methodCC = ()) {
  return CCFactoid(methodCC, "PHP");
}