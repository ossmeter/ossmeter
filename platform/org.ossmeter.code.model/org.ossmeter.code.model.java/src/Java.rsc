module Java

extend lang::java::m3::Core;
import lang::java::m3::AST;
import util::FileSystem;
import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;
import IO;

@memo
private set[loc] build(set[loc] folders, map[str, str] extraRepos) {
  set[loc] result = {};
  for (folder <- folders) {
    set[loc] jars = {};
    int buildResult = buildProject(folder, ());
    if (buildResult != 0) {
      println("Extraction of M3 model failed, model may not be complete");
      result += findJars({folder});
    } else {
      result += { |file:///| + cp | cp <- readFileLines(folder + "cp.txt") };
    }
  }
  return result;
}

private set[loc] getSourceRoots(set[loc] folders) {
	set[loc] result = {};
	for (folder <- folders) {
		// only consult one java file per package tree
		top-down-break visit (crawl(folder)) {
			case directory(d, contents): {
				set[loc] roots = {};
				for (file(f) <- contents, toLowerCase(f.extension) == "java") {
					try {
						for (/package<p:[^;]*>;/ := readFile(f)) {
							packagedepth = size(split(".", trim(p)));
							roots += { d[path = intercalate("/", split("/", d.path)[..-packagedepth])] };
						}
						
						if (roots == {}) { // no package declaration means d is a root 
							roots += { d };	
						}
						
						break;						
					} catch _(_) : ;					
				}
				
				if (roots != {}) {
					result += roots;
				}
				else {
					fail; // continue searching subdirectories
				}
			}
		}
	}
	
	return result;
}


@M3Extractor{java()}
@memo
rel[Language, loc, M3] javaM3(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {  
  rel[Language, loc, M3] result = {};
  
  // TODO: we will add caching on disk again and use the deltas to predict what to re-analyze and what not
  for (/VcsRepository repo := delta, repo.url in checkouts) {
    folders = { checkouts[repo.url] };
    sources = getSourceRoots(folders);
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


@memo
public M3 systemM3(rel[Language, loc, M3] m3s) {
  javaM3s = range(m3s[java()]);
  projectLoc = |java+tmp:///|;
  if (javaM3s == {}) {
    throw undefined("No Java M3s available", projectLoc);
  }
  return composeM3(projectLoc, javaM3s);  
}
