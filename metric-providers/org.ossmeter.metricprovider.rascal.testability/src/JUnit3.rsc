module JUnit3

import lang::java::m3::Core;

set[loc] jUnit3BaseClass = { |java+class:///junit/framework/TestCase|,
                             |java+class:///TestCase| // failsafe
                           };

@memo
private set[loc] getTestClasses(M3 m) {
  return { candidate | <candidate, baseClass> <- m@extends+, baseClass in jUnit3BaseClass };
}

@memo
set[loc] getJUnit3TestMethods(M3 m) {
  set[loc] result = {};
  for (testClass <- getTestClasses(m)) {
    set[loc] candidateMethods = { candidate | candidate <- m@containment[testClass], isMethod(candidate) };
    rel[loc, str] invertedNamesRel = m@names<1,0>;
    for (candidate <- candidateMethods) {
      if (nameStartsWithTest(invertedNamesRel[candidate])) {
        result += candidate;
      }
    }
  }
  return result;
}

set[loc] getJUnit3SetupMethods(M3 m) {
  set[loc] result = {};
  for (testClass <- getTestClasses(m)) {
    set[loc] candidateMethods = { candidate | candidate <- m@containment[testClass], isMethod(candidate) };
    rel[loc, str] invertedNamesRel = m@names<1,0>;
    for (candidate <- candidateMethods) {
      if (isTestSetup(invertedNamesRel[candidate])) {
        result += candidate;
      }
    }
  }
  return result;
}

private bool nameStartsWithTest(str name) {
  return /^test.*/ := name;
}

private bool isTestSetup(str name) {
  return name == "setUp" || name == "tearDown";
}
