module JUnit3

import lang::java::jdt::m3::Core;

loc jUnit3BaseClass = |java+class:///junit/framework/TestCase|;

set[loc] getTestClasses(M3 m) {
  return { candidate | <candidate, baseClass> <- m@extends, baseClass == jUnit3BaseClass };
}

set[loc] getTestMethods(M3 m) {
  set[loc] result = {};
  for (testClass <- getTestClasses(m)) {
    set[loc] candidateMethods = { candidate | candidate <- m@containment[testClass], isMethod(candidate) };
    rel[loc, str] invertedNamesRel = m@names<1,0>;
    for (candidate <- candidateMethods) {
      if (matchesJUnit3NamingConvention(invertedNamesRel[candidate])) {
        result += candidate;
      }
    }
  }
  return result;
}

private bool matchesJUnit3NamingConvention(str name) {
  return /^test.*/ := name || name == "setUp" || name == "tearDown";
}
