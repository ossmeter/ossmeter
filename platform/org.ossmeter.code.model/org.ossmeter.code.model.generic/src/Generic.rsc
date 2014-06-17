module Generic

extend Extractors;
 
set[str] blackListedExtensions = {};

void setBlackListedExtensions(set[str] extensions) {
	  blackListedExtensions += extensions;
	}

@extractor{}
rel[Language, loc, M3] genericM3(loc project, set[loc] files) {
  //if (file.extension in blackListedExtensions) {
  //}
  rel[Language, loc, M3] result = {};
  for (file <- files) {
  
    m = emptyM3(file);
    
    try {
      content = readFile(file);
      chs = size(content);
      lines = chs == 0 ? 1 : (1 | it + 1 | /\n/ := content);
      lastline = size(readFileLines(file)[-1]);
      m@declarations = { <file[scheme="m3+unit"], file(0,chs,<1,0>,<lines - 1,lastline>)> }; 
    }
    catch IO(str msg) : {
      m@messages += [error(msg, file)];
    }
    
    result += { generic(), file, m };
  }
  
  return result;
}