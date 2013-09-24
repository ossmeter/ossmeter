module Manager

import util::ShellExec;
import List;
import IO;
import lang::java::m3::Core;
import analysis::m3::metrics::LOC;

private M3 projectModel = m3(|unknown:///|);

str checkOutRepository(str repositoryURL, loc localStorage) {
    if (!isEmpty(localStorage.ls))
        return "Files already found!!!";
    return readEntireErrStream(createProcess("svn", 
        ["checkout", "--non-interactive", "--trust-server-cert", repositoryURL], localStorage
    ));
}

str createModel(loc project) {
    if (!(projectModel := m3(project))) {
        projectModel = createM3FromDirectory(project);
        return "";
    } else {
        return "source model already present";
    }
}

int countTotalLoc() {
    return countProjectTotalLoc(projectModel);
}

int countCommentedLoc() {
    return countProjectCommentedLoc(projectModel);
}

int countEmptyLoc() {
    return countProjectEmptyLoc(projectModel);
}

int countSourceLoc() {
    return countProjectSourceLoc(projectModel);
}

map[str language, int count] countTotalLocPerLanguage() {
    return countTotalLocPerLanguage(projectModel);
}

map[str language, int count] countSourceLocPerLanguage() {
    return countSourceLocPerLanguage(projectModel);
}
