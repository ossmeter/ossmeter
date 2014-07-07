module PHP

import lang::php::m3::Core;
import lang::php::m3::AST;
import lang::php::m3::FillM3;
import lang::php::ast::AbstractSyntax;
import lang::php::util::Utils;
import org::ossmeter::metricprovider::ProjectDelta; 

import IO;

@M3Extractor{php()}
@memo
public rel[Language, loc, M3] extractM3sPHP(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch)
{
	return { <php(), file, createM3forScript(file, script)> | <php(), file, phpAST(script)> <- extractASTsPHP(project, roots, delta) };
}

@ASTExtractor{php()}
@memo
public rel[Language, loc, AST] extractASTsPHP(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch)
{
	rel[Language, loc, AST] result = {};
	
	for (root <- checkouts<folders>)
	{
		System sys = loadPHPFiles(root);
		result += { <php(), file, (errscript(m) := sys[file]) ? noAST(m) : phpAST(sys[file])> | file <- sys };
	}
	
	return result;
}
