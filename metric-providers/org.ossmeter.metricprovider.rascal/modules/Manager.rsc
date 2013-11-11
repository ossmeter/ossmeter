module Manager

import util::ShellExec;
import List;
import Set;
import IO;
import lang::java::m3::Core;
import util::Math;
import LOC;
import String;

str checkOutRepository(str repositoryURL, int revision, loc localStorage) {
    str result = readEntireStream(createProcess("svn", 
        ["co", "-r", toString(revision), "--non-interactive", "--trust-server-cert", repositoryURL], localStorage
    ));
    return result;
}

M3 createFileM3(loc file) {
  if (file.extension == "java") {
    return createM3FromFile(file);
  }
  return m3(|unknown:///|);
}

bool isValid(M3 fileM3) {
  return !isEmpty(fileM3);
}
