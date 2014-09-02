module JUnit4

import lang::java::jdt::m3::Core;

set[loc] jUnit4Annotations = {
    |java+interface:///org/junit/After|,
    |java+interface:///org/junit/Before|,
    // need to decide on this one, since ignored cases do not add to test coverage estimation but should be counted as test code
    |java+interface:///org/junit/Ignore|, 
    |java+interface:///org/junit/Test|
  };

set[loc] getTestMethods(M3 m) {
  return { testMethod | testMethod <- m@declarations<0>, isMethod(testMethod), m@annotations[testMethod] & jUnit4Annotations != {} };
}
