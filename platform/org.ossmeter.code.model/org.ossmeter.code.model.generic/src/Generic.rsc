module Generic

import util::FileSystem;
extend analysis::m3::Core;
extend analysis::m3::AST;
import org::ossmeter::metricprovider::ProjectDelta;

import IO;
import List;
import String;


@M3Extractor{generic()}
@memo
rel[Language, loc, M3] genericM3(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
  //if (file.extension in blackListedExtensions) {
  //}
  rel[Language, loc, M3] result = {};
  folders = checkouts<folders>;
  for (folder <- folders, file <- visibleFiles(folder)) { 
    m = emptyM3(file);
    
    try {
      content = readFile(file);
      chs = size(content);
      numLines = chs == 0 ? 1 : (1 | it + 1 | /\n/ := content);
      lastline = chs == 0 ? 1 : size(readFileLines(file)[-1]);
      m@declarations = { <file[scheme="m3+unit"], file(0,chs,<1,0>,<numLines, lastline>)> }; // TODO remove
    }
    catch IO(str msg) : {
      m@messages += [error(msg, file)];
    }
    
    result += { <generic(), file, m> };
  }
  
  return result;
}

@ASTExtractor{generic()}
@memo
rel[Language, loc, AST] genericAST(loc project, ProjectDelta delta, map[loc repos,loc folders] checkouts, map[loc,loc] scratch) {
	return {<generic(), file, lines(readFileLines(file))> | folder <- checkouts<folders>, file <- visibleFiles(folder)};
}