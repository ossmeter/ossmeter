module Java

extend lang::java::m3::Core;
import lang::java::m3::AST;
import util::FileSystem;
import org::ossmeter::metricprovider::ProjectDelta;

@M3Extractor{java()}
@memo
rel[Language, loc, M3] javaM3(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
  println("extracting Java M3 for <project>");
  
  rel[Language, loc, M3] result = {};
  
  // TODO: we will add caching on disk again and use the deltas to predict what to re-analyze and what not
  for (/VcsRepository repo := delta, repo.url in checkouts) {
    folders = { checkouts[repo.url] };
    sources = findSourceRoots(folders);
    jars = findJars(folders);
    setEnvironmentOptions(jars + sources, sources);
  
    result += {<java(), f, createM3FromFile(f)> | source <- sources, f <- find(source, "java")};
  }
  
  return result;
}

@ASTExtractor{java()}
@memo
rel[Language, loc, AST] javaAST(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
  println("extracting Java ASTs for <project>");
  
  rel[Language, loc, AST] result = {};
  
  // TODO: we will add caching on disk again and use the deltas to predict what to re-analyze and what not
  for (/VcsRepository repo := delta, repo.url in checkouts) {
    folders = { checkouts[repo.url] };
    sources = findSourceRoots(folders);
    jars = findJars(folders);
    setEnvironmentOptions(jars + sources, sources);
  
    result += {<java(), f, declaration(createAstFromFile(f, true))> | source <- sources, f <- find(source, "java")};
  }
  
  return result;
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
