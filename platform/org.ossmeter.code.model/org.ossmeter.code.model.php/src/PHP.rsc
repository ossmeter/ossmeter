module PHP

import lang::php::m3::Core;
import lang::php::m3::AST;
import lang::php::m3::FillM3;
import lang::php::m3::Calls;
import lang::php::ast::AbstractSyntax;
import lang::php::ast::System;
import lang::php::util::Utils;
import org::ossmeter::metricprovider::ProjectDelta; 

import IO;
import Message;
import Relation;

@M3Extractor{php()}
@memo
public rel[Language, loc, M3] extractM3sPHP(loc project, ProjectDelta delta, map[loc repos, loc folders] checkouts, map[loc, loc] scratch)
{
	return { <php(), file, createM3forScript(file, script)> | <php(), file, phpAST(script)> <- extractASTsPHP(project, delta, checkouts, scratch) };
}

@ASTExtractor{php()}
@memo
public rel[Language, loc, AST] extractASTsPHP(loc project, ProjectDelta delta, map[loc repos, loc folders] checkouts, map[loc, loc] scratch)
{
	rel[Language, loc, AST] result = {};
	
	for (root <- checkouts<folders>)
	{
		System sys = loadPHPFiles(root);
		result += { <php(), file, (errscript(m) := sys[file]) ? noAST(error(m, file)) : phpAST(sys[file])> | file <- sys };
	}
	
	return result;
}

@memo
public M3 composeM3s(rel[Language, loc, M3] m3s) {
	return composeM3(|project:///|, range(m3s[php()]));
}

@memo
public tuple[M3 m3, rel[loc, loc] callResolution, rel[loc, loc] fieldAccessResolution] resolveMethodCallsAndFieldAccesses(M3 m3) {
	calls = resolveUnknownMethodCalls(m3);	
	accesses = resolveUnknownFieldAccesses(m3);
	
	m3 = replaceUnknownMethodCalls(m3, calls);
	m3 = replaceUnknownFieldAccesses(m3, accesses);	
	
	return <m3, calls, accesses>;
}

@memo
public M3 systemM3(rel[Language, loc, M3] m3s) {
	M3 m3 = composeM3s(m3s);
	m3 = resolveMethodCallsAndFieldAccesses(m3)[0];
	return m3;
}
