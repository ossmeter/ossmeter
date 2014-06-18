module Generic

import util::FileSystem;
import analysis::m3::Core;
import analysis::m3::AST;
import org::ossmeter::metricprovider::ProjectDelta;
import IO;
import String;
 
set[str] blackListedExtensions = {};

void setBlackListedExtensions(set[str] extensions) {
	  blackListedExtensions += extensions;
	}

@M3Extractor{}
rel[Language, loc, M3] genericM3(loc project, set[loc] workingCopyFolders, ProjectDelta delta) {
  //if (file.extension in blackListedExtensions) {
  //}
  rel[Language, loc, M3] result = {};
  for (folder <- workingCopyFolders, file <- files(folder)) {  
    m = emptyM3(file);
    
    try {
      content = readFile(file);
      chs = size(content);
      numLines = chs == 0 ? 1 : (1 | it + 1 | /\n/ := content);
      lastline = size(readFileLines(file)[-1]);
      m@declarations = { <file[scheme="m3+unit"], file(0,chs,<1,0>,<numLines, lastline>)> }; // TODO remove
    }
    catch IO(str msg) : {
      m@messages += [error(msg, file)];
    }
    
    result += { <generic(), file, m> };
  }
  
  return result;
}

@ASTExtractor{}
rel[Language, loc, \AST] genericAST(loc project, set[loc] workingCopyFolders, ProjectDelta delta) {
	return {<generic(), file, lines(readFileLines(file))> | folder <- workingCopyFolders, file <- files(folder)};
}