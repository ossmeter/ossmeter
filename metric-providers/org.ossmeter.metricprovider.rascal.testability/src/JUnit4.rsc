module JUnit4

extend JUnit3;
import lang::java::m3::Core;

set[loc] jUnit4TestSetupAnnotations = {
    |java+interface:///org/junit/After|,
    |java+interface:///org/junit/Before|,
    |java+interface:///org/junit/AfterClass|,
    |java+interface:///org/junit/BeforeClass|
  };
  
loc jUnit4TestAnnotation = |java+interface:///org/junit/Test|;

@memo
private set[loc] methodsWithIgnoreAnnotation(M3 m) {
  // We collect all methods that have ignore annotation as well as methods in classes that have ignore annotations
  set[loc] result = {};
  loc jUnit4IgnoreAnnotation = |java+interface:///org/junit/Ignore|;
  for (loc entity <- m@annotations) {
    if (jUnit4IgnoreAnnotation in m@annotations[entity]) {
      if (isClass(entity)) {
        result += { method | method <- m@containment[entity], isMethod(method) };
      } else if (isMethod(entity)){
        result += entity;
      }
    }
  }
  return result;
}

set[loc] getJUnit4TestMethods(M3 m) {
  return getJUnit3TestMethods(m) + { testMethod | testMethod <- m@declarations<0>, isMethod(testMethod), jUnit4TestAnnotation in m@annotations[testMethod] } - methodsWithIgnoreAnnotation(m);
}

set[loc] getJUnit4SetupMethods(M3 m) {
  return getJUnit3SetupMethods(m) + { testMethod | testMethod <- m@declarations<0>, isMethod(testMethod), (m@annotations[testMethod] & jUnit4TestSetupAnnotations) != {} } - methodsWithIgnoreAnnotation(m);
}
