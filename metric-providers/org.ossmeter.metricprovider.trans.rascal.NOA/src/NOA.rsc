module NOA

import lang::java::m3::Core;
import lang::php::m3::Core;
import List;
import String;
import Map;
import org::ossmeter::metricprovider::ProjectDelta;
import IO;
import ValueIO;

map[loc class, int noa] getNOA(ProjectDelta delta, map[loc, loc] workingCopyFolders, rel[loc, M3] m3s, bool(loc) isClass, bool(loc) isAttribute)
{ 
	map[loc class, int noa] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[file])
	{
		for (<cl, a> <- m3@containment, isClass(cl), isAttribute(a))
		{
			result[cl]?0 += 1;
		}
	}
	
	return result;
}


@metric{NOAJava}
@doc{Compute the number of attributes per Java class}
@friendlyName{Number of attributes Java}
@appliesTo{java()}
map[loc class, int noa] NOAJava(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	return getNOA(delta, workingCopyFolders, m3s[java()], lang::java::m3::Core::isClass, lang::java::m3::Core::isField);
}

//@metric{NOAPHP}
@doc{Compute the number of attributes per PHP class}
@friendlyName{Number of attributes PHP}
@appliesTo{php()}
map[loc class, int noa] NOAPHP(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[loc, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	return getNOA(delta, workingCopyFolders, m3s[php()], lang::php::m3::Core::isClass, lang::php::m3::Core::isField);
}
