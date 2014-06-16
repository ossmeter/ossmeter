@doc{
Synopsis: a callback framework for externally registering M3 extractors.

Description:

This module facilitates extension for different kinds of M3 extractors (e.g. for different languages)  
}
module analysis::m3::Extractors

extend analysis::m3::Core;
import util::FileSystem;
import String;

data Language(str version = "")
  = generic()
  ;
  
data \AST(loc file = |unknown:///|)
  = \genericAST(str contents)
  | noAST(Message msg)
  ;

alias M3Extractor = rel[Language, loc, M3] (loc project, set[loc] files);
alias ASTExtractor = rel[Language, loc, \AST] (loc project, set[loc] files);

private set[M3Extractor] M3Registry = {};
private set[ASTExtractor] ASTRegistry = {};

void registerExtractor(M3Extractor extractor) {
  M3Registry += extractor;
}

void registerExtractor(ASTExtractor extractor) {
  ASTRegistry += extractor;
}

rel[Language, loc, M3] extractM3(loc project) = extractM3(project, {project});
rel[Language, loc, \AST] extractAST(loc project) = extractAST(project, {project});

@doc{
Synopsis: runs all extractors on a project to return one M3 model per file in the project
}
rel[Language, loc, M3] extractM3(loc project, set[loc] roots) {
  allFiles = { *files(r) | r <- roots };
  
  return { *extractor(project, allFiles) | extractor <- M3Registry }; 
}

rel[Language, loc, \AST] extractAST(loc project, set[loc] roots) {
  allFiles = { *files(r) | r <- roots };
  
  return { *extractor(project, allFiles) | extractor <- ASTRegistry }; 
}
