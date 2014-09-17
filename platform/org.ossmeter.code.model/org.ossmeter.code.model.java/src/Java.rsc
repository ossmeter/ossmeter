module Java

extend lang::java::m3::Core;
import lang::java::m3::AST;
import util::FileSystem;
import org::ossmeter::metricprovider::ProjectDelta;
import IO;

@memo
private set[loc] build(set[loc] folders, map[str, str] extraRepos) {
  set[loc] result = {};
  println("resolving dependencies");
  for (folder <- folders) {
    set[loc] jars = {};
    int buildResult = buildProject(folder, ());
    if (buildResult != 0) {
      println("failed, M3 model may not be complete");
      result += findJars({folder});
    } else {
      println("succeeded");
      result += { |file:///| + cp | cp <- readFileLines(folder + "cp.txt") };
    }
  }
  return result;
}

@M3Extractor{java()}
@memo
rel[Language, loc, M3] javaM3(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
  println("extracting Java M3 for <project>");
  
  rel[Language, loc, M3] result = {};
  
  // TODO: we will add caching on disk again and use the deltas to predict what to re-analyze and what not
  for (/VcsRepository repo := delta, repo.url in checkouts) {
    folders = { checkouts[repo.url] };
    sources = findSourceRoots(folders);
    // TODO: need to find a way to get external dependencies (if we want to support them)
    jars = build(folders, ());
    
    setEnvironmentOptions(jars, sources);
  
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
    // TODO: need to find a way to get external dependencies (if we want to support them)
    jars = build(folders, ());
    setEnvironmentOptions(jars, sources);
      
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

// this may become more interesting if we try to recover dependency information from meta-data
// for now we do a simple file search
set[loc] findClassFiles(set[loc] checkouts) {
  return {*find(ch, "class") | ch <- checkouts};
}
