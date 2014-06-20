module NOM

import lang::java::m3::Core;
import lang::php::m3::Core;
import List;
import String;
import Map;
import ValueIO;
import IO;
import analysis::m3::Core;
import org::ossmeter::metricprovider::ProjectDelta;


map[loc class, int nom] getNOM(ProjectDelta delta, map[loc, loc] workingCopyFolders, rel[loc, M3] m3s, bool(loc) isClass, bool(loc) isMethod)
{ 
	map[loc class, int nom] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[file])
	{
		for (<cl, m> <- m3@containment, isClass(cl), isMethod(m))
		{
			result[cl]?0 += 1;
		}
	}
	
	return result;
}

@metric{NOMJava}
@doc{Compute the number of methods per Java class}
@friendlyName{Number of methods Java}
@appliesTo{java()}
map[loc class, int nom] NOMJava(
	ProjectDelta delta = \empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	return getNOM(delta, workingCopyFolders, m3s[java()], lang::java::m3::Core::isClass, lang::java::m3::Core::isMethod);
}

//@metric{NOMPHP}
@doc{Compute the number of methods per PHP class}
@friendlyName{Number of methods PHP}
@appliesTo{php()}
map[loc class, int nom] NOMPHP(
	ProjectDelta delta = \empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	return getNOM(delta, workingCopyFolders, m3s[php()], lang::php::m3::Core::isClass, lang::php::m3::Core::isMethod);
}
