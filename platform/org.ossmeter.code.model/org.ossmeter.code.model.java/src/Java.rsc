module Java

extend lang::java::m3::Core;
import lang::java::m3::AST;
import util::FileSystem;
import org::ossmeter::metricprovider::ProjectDelta;

data Language(str version="") = java();

@M3Extractor{}
rel[Language, loc, M3] javaM3(loc project, set[loc] checkouts, ProjectDelta delta) {
  println("extracting Java M3 for <project>");
  
  sources = findSourceRoots(checkouts);
  jars = findJars(checkouts);
  
  setEnvironmentOptions(jars + sources, sources);
  
  return {<java(), f, createM3FromFile(f)> | c <- checkouts, f <- find(c, "java")};
}

// TODO add an @ASTExtractor{} function

// this will become more interesting if we try to recover build information from meta-data
// for now we do a simple file search
set[loc] findSourceRoots(set[loc] checkouts) {
  bool containsFile(loc d) = isDirectory(d) ? (x <- d.ls && x.extension == "java") : false;
  return {*find(dir, containsFile) | dir <- checkouts};       
}

// this may become more interesting if we try to recover dependency information from meta-data
// for now we do a simple file search
set[loc] findJars(set[loc] checkouts) {
  return {*find(ch, "jar") | ch <- checkouts};
}
