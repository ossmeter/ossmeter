module PHP

import lang::php::m3::Core;
import lang::php::m3::AST;
import lang::php::m3::FillM3;
import lang::php::ast::AbstractSyntax;
import lang::php::util::Utils;
import org::ossmeter::metricprovider::ProjectDelta; 

import IO;


@M3Extractor{}
public rel[Language, loc, M3] extractM3sPHP(loc project, set[loc] roots, ProjectDelta delta)
{
	return { <php(), file, createM3forScript(file, script)> | <php(), file, phpAST(script)> <- extractASTsPHP(project, roots, delta) };
}

@ASTExtractor{}
@memo
public rel[Language, loc, AST] extractASTsPHP(loc project, set[loc] roots, ProjectDelta delta)
{
	rel[Language, loc, AST] result = {};
	
	for (root <- roots)
	{
		System sys = loadPHPFiles(root);
		result += { <php(), file, (errscript(m) := sys[file]) ? noAST(m) : phpAST(sys[file])> | file <- sys };
	}
	
	return result;
}