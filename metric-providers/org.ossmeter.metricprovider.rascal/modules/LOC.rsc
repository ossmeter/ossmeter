module LOC

import lang::java::m3::Core;
import analysis::graphs::Graph;
import IO;
import List;
import String;
import Set;

alias locResult = tuple[int total, int comment, int empty, int source];

int countCommentedLoc(M3 projectModel, loc cu) 
  = (0 | it + (doc.end.line - doc.begin.line + 1 - checkForSourceLines(doc)) | doc <- projectModel@documentation[cu]); 

private int checkForSourceLines(loc commentLoc, str fileContents) {
  str comment = substring(fileContents, commentLoc.offset, commentLoc.offset + commentLoc.length);
  // We will check to see if there are any source code in the commented lines
  loc commentedLines = commentLoc;
  // start from start of the line
  commentedLines.begin.column = 0;
  // increase to the next line to cover the full line
  commentedLines.end.line += 1;
  // we won't take any character from the next line
  commentedLines.end.column = 0;
  list[str] contents = split("\n", fileContents)[commentedLines.begin.line-1..commentedLines.end.line-1];
  str commentedLinesSrc = intercalate("\n", contents);
  // since we look till the start of the next line, we need to make sure we remove the extra \n from the end  
  if (isEmpty(last(contents)))
    commentedLinesSrc = replaceLast(commentedLinesSrc, "\n" , "");
  return size(split(trim(comment), trim(commentedLinesSrc)));
}

str removeComments(str contents, M3 fileM3) {
  set[loc] compilationUnit = {src | <name, src> <- fileM3@declarations, isCompilationUnit(name)};
  assert size(compilationUnit) == 1 : "More than one compilation unit in a file???";
  loc cu = getOneFrom(compilationUnit);
  list[str] listContents = split("\n", contents);
  list[str] result = listContents;
  for (loc commentLoc <- fileM3@documentation[cu]) {
    // remove comments
    result = result - slice(listContents, commentLoc.begin.line - 1, commentLoc.end.line - commentLoc.begin.line + 1);
  }
  return intercalate("\n", result);
}

locResult countLoc(loc file) {
  locResult result = <-1,-1,-1,-1>;
  
  str fileContents = readFile(file);
  
  result.total = size(split("\n", fileContents));
  result.comment = -1;
  result.empty = -1;
  result.source = result.total;
  
  return result;
}

loc getCompilationUnit(M3 fileM3) {
  set[loc] compilationUnit = {src | <name, src> <- fileM3@declarations, isCompilationUnit(name)};
  assert size(compilationUnit) == 1 : "More than one compilation unit in a file???";
  loc compilationUnitSrc = getOneFrom(compilationUnit);
  
  return compilationUnitSrc;
}

locResult countLoc(M3 fileM3) {
  locResult result = <-1,-1,-1,-1>;
  
  loc compilationUnitSrc = getCompilationUnit(fileM3);
  
  str fileContents = readFile(compilationUnitSrc);
  
  result.total = compilationUnitSrc.end.line;
  result.comment = countCommentedLoc(fileM3, fileContents);
  result.empty = countEmptyLoc(fileM3, fileContents);
  result.source = result.total - result.comment - result.empty;
  return result;
}

int countTotalLoc(M3 fileM3) {
  loc compilationUnit = getCompilationUnit(fileM3);
  return compilationUnit.end.line;
}

int countCommentedLoc(M3 fileM3, str fileContents) {
  int result = 0;
  set[loc] comments = { src | <name, src> <- fileM3@documentation, isCompilationUnit(name) };
  for (source <- comments) {
    result += source.end.line - source.begin.line + 1 - checkForSourceLines(source, fileContents);
  }
  return result;
}

int countEmptyLoc(M3 fileM3, str fileContents)
  =  (0 | it + 1 | /^\s*$/ <- split("\n", removeComments(fileContents, fileM3)));
  
tuple[str language, int count] locPerLanguage(M3 fileM3) {
  return <"java", countTotalLoc(fileM3)>;
}

tuple[str language, int count] locPerLanguage(loc file) {
  return <file.extension, size(readFileLines(file))>;
}
