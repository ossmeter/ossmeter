module Main

extend Extractors;
 
set[str] blackListedExtensions = {};

void setBlackListedExtensions(set[str] extensions) {
	  blackListedExtensions += extensions;
	}

@extractor{generic()}
M3 genericM3(loc file) {
  //if (file.extension in blackListedExtensions) {
  //}
  m = m3(file);
  
  m@declarations = { };
  m@uses = { };
  m@containment = { };
  m@documentation = { };
  m@modifiers = { };
  m@messages = [ ];
  m@names = { };
  m@types = { };
  
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
  
  return m;
}