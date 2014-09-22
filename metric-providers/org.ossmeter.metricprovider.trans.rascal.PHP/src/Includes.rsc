module Includes

import lang::php::ast::AbstractSyntax;
import lang::php::ast::System;
import lang::php::ast::Scopes;

import lang::php::analysis::includes::IncludesInfo;
import lang::php::analysis::includes::QuickResolve;
import lang::php::analysis::evaluators::DefinedConstants;
import lang::php::util::LocUtils;

import Prelude;
import util::Math;

private IncludesInfo buildIncludesInfo(System sys, loc baseloc) {
	map[loc,set[ConstItemExp]] loc2consts = ( l : { cdef[e=normalizeExpr(cdef.e, baseloc)]  | cdef <- getScriptConstDefs(sys[l]) } | l <- sys);
	rel[ConstItem,loc,Expr] constrel = { < (classConst(cln,cn,ce) := ci) ? classConst(cln,cn) : normalConst(ci.constName), l, ci.e > | l <- loc2consts, ci <- loc2consts[l] };

	map[str, Expr] constMap = ( cn : ce | ci:normalConst(cn) <- constrel<0>, csub := constrel[ci,_], size(csub) == 1, ce:scalar(sv) := getOneFrom(csub), encapsed(_) !:= sv );  
	if ("DIRECTORY_SEPARATOR" notin constMap)
		constMap["DIRECTORY_SEPARATOR"] = scalar(string("/"));
	if ("PATH_SEPARATOR" notin constMap)
		constMap["PATH_SEPARATOR"] = scalar(string(":"));

	map[str, map[str, Expr]] classConstMap = ( );
	for (ci:classConst(cln,cn) <- constrel<0>, csub := constrel[ci,_], size(csub) == 1, ce:scalar(sv) := getOneFrom(csub), encapsed(_) !:= sv) {
		if (cln in classConstMap) {
			classConstMap[cln][cn] = ce;
		} else {
			classConstMap[cln] = ( cn : ce );
		}
	}

	return includesInfo(loc2consts, constrel, constMap, classConstMap);
}

private set[loc] matchIncludes(System sys, Expr includeExpr, loc baseLoc, set[loc] libs = { }) {

	scalars = [ s | /scalar(string(s)) := includeExpr ];
	
	if (scalars == []) {
		return {};
	}
	
	// Create the regular expression representing the include expression
	str re = "^.*" + intercalate(".*", scalars) + "$";

	// Filter the includes to just return those that match the regular expression
	set[loc] filteredIncludes = { l | l <- (sys<0> + libs), rexpMatch(l.path,re) }; 

	// Just return the result of applying the regexp match, we may want to do
	// some caching, etc here in the future	
	return filteredIncludes;	
}

private tuple[rel[loc,loc] resolved, rel[loc, str] notFound, set[loc] unresolved] resolveIncludes(System sys, IncludesInfo iinfo, loc toResolve, loc baseLoc, set[loc] libs = { }, bool checkFS=false) {
	rel[loc, loc] resolved = {};
	rel[loc, str] notFound = {};

	//iprintln("toResolve: <toResolve>");

	Script scr = sys[toResolve];
	includes = { < i@at, i > | /i:include(_,_) := scr };
	if (size(includes) == 0) return <{}, {}, {}>;
	
	// Step 1: simplify the include expression using a variety of techniques,
	// such as simulating function calls, replacing magic constants, and
	// performing string concatenations
	includes = { < l, normalizeExpr(replaceConstants(i,iinfo), baseLoc) > | < l, i > <- includes };
	
	//iprintln("includes: <includes>");
	
	// Step 2: if we have a scalar expression then see if we can match it to a file, it should
	// be something in the set of files that make up the system; in this case we
	// should be able to match it to a unique file
	
	for (iitem:< _, i > <- includes, scalar(string(s)) := i.expr) {
		try {
			//println("Resolving <s>");
			
			iloc = calculateLoc(sys<0>,toResolve,baseLoc,s,checkFS=checkFS,pathMayBeChanged=false);
			
			//iprintln("Result: <iloc>");
			
			resolved += {<i@at, iloc >};
		} catch UnavailableLoc(_) : {
			notFound += {<i@at, s>};
		}
	}

	// Step 3: if we have a non-scalar expression, try matching to see if we can
	// match the include to one or more potential files; if this matches multiple
	// possible files, that's fine, this is a conservative estimation so we may
	// find files that will never actually be included in practice
	for (iitem:< _, i > <- includes, scalar(string(_)) !:= i.expr) {
		possibleMatches = matchIncludes(sys, i, baseLoc, libs=libs);	
		resolved = resolved + { < i@at, l > | l <- possibleMatches };
	}

	unresolved = domain(includes) - (domain(resolved) + domain(notFound));

	return <resolved, notFound, unresolved>;
}

@memo
private tuple[rel[loc,loc] resolved, rel[loc, str] notFound, set[loc] unresolved] resolveIncludes(System sys, loc baseLoc, set[loc] libs = {}) {
	ii = buildIncludesInfo(sys, baseLoc);
	
	rel[loc, loc] resolved = {};
	rel[loc, str] notFound = {};
	set[loc] unresolved = {};
	
	for (f <- sys) {
		<r, nf, u> = resolveIncludes(sys, ii, f, baseLoc, libs=libs);
		resolved += r;
		notFound += nf;
		unresolved += u;
	}
	
	return <resolved, notFound, unresolved>;
}

public map[int, int] includeResolutionHistogram(System sys, loc baseLoc, set[loc] libs = {}) {

	map[int, int] result = ();

	<r, nf, u> = resolveIncludes(sys, baseLoc, libs=libs);

	result[0] = size(u) + size(nf);
	
	for (i <- domain(r)) {
		result[size(r[i])]?0 += 1;
	}
	
	return result;
}
