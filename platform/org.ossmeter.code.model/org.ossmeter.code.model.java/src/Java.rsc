module Java

extend Extractors;
import lang::java::m3::Core;
import lang::java::m3::AST;
import util::FileSystem;

data Language(str version="") = java();

@extractor{}
rel[Language, loc, M3] javaM3(loc project, set[loc] checkouts) {
  println("extracting Java M3 for <project>");
  
  sources = findSourceRoots(checkouts);
  jars = findJars(checkouts);
  
  setEnvironmentOptions(jars + sources, sources);
  
  return {<java(), f, createM3FromFile(f)> | c <- checkouts, f <- find(c, "java")};
}

// this will become more interesting if we try to recover build information from meta-data
// for now we do a simple file search
// we have to find out what are "external" dependencies and also measure these!
set[loc] findSourceRoots(set[loc] checkouts) {
  bool containsFile(loc d) = isDirectory(d) ? (x <- d.ls && x.extension == "java") : false;
  return {*find(dir, containsFile) | dir <- checkouts};       
}

// this may become more interesting if we try to recover dependency information from meta-data
// for now we do a simple file search
set[loc] findJars(set[loc] checkouts) {
  return {*find(ch, "jar") | ch <- checkouts};
}
