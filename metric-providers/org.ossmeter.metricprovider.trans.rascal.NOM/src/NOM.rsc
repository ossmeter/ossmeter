module NOM

import lang::java::m3::Core;
import lang::php::m3::Core;
import List;
import String;
import Map;
import ValueIO;
import IO;
import analysis::m3::Core;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

int numberOfMethodsJava(loc cl, M3 model) = size([ m | m <- model@containment[cl], lang::java::m3::Core::isMethod(m)]);
int numberOfMethodsPHP(loc cl, M3 model) = size([ m | m <- model@containment[cl], lang::php::m3::Core::isMethod(m)]);

map[loc class, int nom] getNOMJava(M3 m3) {
  return ( cl:numberOfMethodsJava(cl, m3) | <cl,_> <- m3@containment, lang::java::m3::Core::isClass(cl));
}

map[loc class, int nom] getNOMPHP(M3 m3) {
  return ( cl:numberOfMethodsPHP(cl, m3) | <cl,_> <- m3@containment, lang::php::m3::Core::isClass(cl));
}

@metric{NOMJava}
@doc{Compute the number of methods per Java class}
@friendlyName{Number of methods Java}
@appliesTo{java()}
map[loc class, int nom] NOMJava(
	ProjectDelta delta = \empty(),
	map[str, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	map[loc class, int nom] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[java(), file])
	{  
		result += getNOMJava(m3);
	}
	
	return result;
}

@metric{NOMPHP}
@doc{Compute the number of methods per PHP class}
@friendlyName{Number of methods PHP}
@appliesTo{php()}
map[loc class, int nom] NOMPHP(
	ProjectDelta delta = \empty(),
	map[str, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	map[loc class, int nom] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[php(), file])
	{  
		result += getNOMPHP(m3);
	}
	
	return result;
}
