module Java

extend Extractors;
import lang::java::m3::Core;
import lang::java::m3::AST;

data Language(str version="") = java();

@extractor{}
rel[Language, loc, M3] javaM3(loc project, set[loc] files) {
  println("extracting Java M3 for <project>");
  setEnvironmentOptions(classPathForProject(project), sourceRootsForProject(project));
  //compliance = getProjectOptions(project)["org.eclipse.jdt.core.compiler.compliance"];
  
  return {<java(), f, createM3FromFile(f, javaVersion=compliance)> | f <- files, f.extension == "java"};
}
