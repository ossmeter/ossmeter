module Java

extend Extractors;
import lang::java::m3::Core;
import lang::java::m3::AST;

data Language(str version="") = java();

@extractor{java()}
set[M3] javaM3(loc project, set[loc] files) {
  set[M3] result = {};
  
  setEnvironmentOptions(classPathForProject(project), sourceRootsForProject(project));
  //compliance = getProjectOptions(project)["org.eclipse.jdt.core.compiler.compliance"];
  
  for (f <- files, f.extension == "java") {
    result += {createM3FromFile(f, javaVersion=compliance)};
  }
  
  return result;
}
