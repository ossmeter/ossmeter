module NOM

import List;
import String;
import Map;
import ValueIO;
import IO;
import analysis::m3::Core;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

int numberOfMethods(loc cl, M3 model) = size([ m | m <- model@containment[cl], isMethod(m)]);

map[loc class, int nom] getNOM(M3 m3) {
  return (cl:numberOfMethods(cl, m3) | <cl,_> <- m3@containment, isClass(cl));
}

@metric{NOM}
@doc{Compute your NOM}
@friendlyName{Number of methods}
@appliesTo{java()}
map[loc class, int nom] NOM(
	ProjectDelta delta = \empty(),
	map[str, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = {})
{
	map[loc class, int nom] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[java(), file])
	{  
		result += getNOM(m3);
	}
	
	return result;
}