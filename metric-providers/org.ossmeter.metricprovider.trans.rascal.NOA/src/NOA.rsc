module NOA

import lang::java::m3::Core;
import lang::php::m3::Core;
import List;
import String;
import Map;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import IO;
import ValueIO;

int numberOfAttributesJava(loc cl, M3 model) = size([ m | m <- model@containment[cl], lang::java::m3::Core::isField(m)]);
int numberOfAttributesPHP(loc cl, M3 model) = size([ m | m <- model@containment[cl], lang::php::m3::Core::isField(m)]);

map[loc class, int nom] getNOAJava(M3 m3) {
  return (cl:numberOfAttributesJava(cl, m3) | <cl,_> <- m3@containment, lang::java::m3::Core::isClass(cl));
}

map[loc class, int nom] getNOAPHP(M3 m3) {
  return (cl:numberOfAttributesPHP(cl, m3) | <cl,_> <- m3@containment, lang::php::m3::Core::isClass(cl));
}

@metric{NOAJava}
@doc{Compute the number of attributes per Java class}
@friendlyName{Number of attributes Java}
@appliesTo{java()}
map[loc class, int noa] NOAJava(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[str, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	map[loc class, int noa] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[java(), file])
	{  
		result += getNOAJava(m3);
	}
	
	return result;
}

@metric{NOAPHP}
@doc{Compute the number of attributes per PHP class}
@friendlyName{Number of attributes PHP}
@appliesTo{php()}
map[loc class, int noa] NOAPHP(
	ProjectDelta delta = ProjectDelta::\empty(),
	map[str, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	map[loc class, int noa] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[php(), file])
	{  
		result += getNOAPHP(m3);
	}
	
	return result;
}
