module NOA

import lang::java::m3::Core;
import List;
import String;
import Map;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import IO;
import ValueIO;

int numberOfAttributes(loc cl, M3 model) = size([ m | m <- model@containment[cl], isField(m)]);

map[loc class, int nom] getNOA(M3 m3) {
  return (cl:numberOfAttributes(cl, m3) | <cl,_> <- m3@containment, isClass(cl));
}

@metric{NOA}
@doc{Compute your NOA}
@friendlyName{Number of attributes}
@appliesTo{java()}
map[loc class, int noa] NOA(
	ProjectDelta delta = \empty(),
	map[str, loc] workingCopyFolders = (),
	rel[Language, loc, M3] m3s = ())
{
	map[loc class, int noa] result = ();
	changed = getChangedFilesInWorkingCopyFolders(delta, workingCopyFolders);
	
	for (file <- changed, m3 <- m3s[java(), file])
	{  
		result += getNOA(m3);
	}
	
	return result;
}
