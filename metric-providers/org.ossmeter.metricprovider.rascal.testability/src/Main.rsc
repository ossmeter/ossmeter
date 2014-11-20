module Main

import Set;
import Relation;
import IO;
import util::ValueUI;
import util::Math;
import analysis::graphs::Graph;
extend lang::java::m3::Core;
import JUnit4;
import Java;
import org::ossmeter::metricprovider::MetricProvider;


@metric{TestCoverage}
@doc{This is a static over-estimation of test coverage: which code is executed in the system when all JUnit test cases are executed? We approximate
this by using the static call graphs and assuming every method which can be called, will be called. This leads to an over-approximation,
as compared to a dynamic code coverage analysis, but the static analysis does follow the trend and a low code coverage here is an good indicator
for a lack in testing effort for the project.}
@friendlyName{Static Estimation of test coverage}
@appliesTo{java()}
@historic{}
real estimateTestCoverage(rel[Language, loc, M3] m3s = {}) {
  m = systemM3(m3s);
  implicitContainment = getImplicitContainment(m);
  implicitCalls = getImplicitCalls(m, implicitContainment);
  
  fullContainment = m@containment + implicitContainment;
  
  liftedInvocations = {};
  for (<caller, callee> <- m@methodInvocation) {
    if (isMethod(caller)) {
      liftedInvocations += { <caller, callee> };
      continue;
    }
    inverseContainment = m@containment<1,0>;
    if (caller.scheme == "java+initializer") {
      assert size(inverseContainment[caller]) == 1 : "Error: Multiple parents for 1 entity";
      caller = getOneFrom(inverseContainment[caller]);
    }
    if (caller.scheme == "java+class" || caller.scheme == "java+anonymousClass" || caller.scheme == "java+enum") {
      for (meth <- fullContainment[caller], meth.scheme == "java+constructor") {
        liftedInvocations += { <meth, callee> };
      }
    }
  }
  
  fullCallGraph = liftedInvocations + implicitCalls + m@methodOverrides<1,0>;
  allTestMethods = getJUnit4TestMethods(m) + getJUnit4SetupMethods(m);
  interfaceMethods = { meth | <entity, meth> <- m@containment, isMethod(meth), isInterface(entity) };
  set[loc] reachableMethods = { meth | meth <- reach(fullCallGraph, allTestMethods), meth in m@declarations<0> } - allTestMethods - interfaceMethods;
  int totalDefinedMethods = (0 | it + 1 | meth <- m@declarations<0> - allTestMethods - interfaceMethods, isMethod(meth));
  return (100.0 * size(reachableMethods)) / totalDefinedMethods;
}

/*  
 * Adding implicit calls between a constructor and its super
 */
private rel[loc, loc] getImplicitCalls(M3 m, rel[loc, loc] implicitContainment) {
  fullContainment = m@containment + implicitContainment;  
  
  rel[loc, loc] implicitCalls = {};
  
  for (<ch, par> <- m@extends) {
    if (par in implicitContainment<0>) {
      for (con <- fullContainment[ch], con.scheme == "java+constructor") {
        assert(size(implicitContainment[par]) == 1) : "Found more than one implicit constuctor";
        implicitCalls += <con, getOneFrom(implicitContainment[par])>;
      }
    }
  }
  
  return implicitCalls;
}

/*
 * Adding implicit constructors to all classes that don't define any constructor
 */
private rel[loc, loc] getImplicitContainment(M3 m) {
  rel[loc, loc] implicitContainment = {};
  
  for (cl <- m@declarations<0>, cl.scheme == "java+class" || cl.scheme == "java+anonymousClass" || cl.scheme == "java+enum") {
    allMethods = { candidate | candidate <- m@containment[cl], isMethod(candidate) };
    
    if (!any(meth <- allMethods, meth.scheme == "java+constructor")) {
      possibleNames = m@names<1,0>[cl];
      assert(size(possibleNames) <= 1) : "Found more than one simple name entry for qualified name <cl>: <possibleNames>";
      className = isEmpty(possibleNames) ? "" : getOneFrom(possibleNames);
      defaultConstructorLOC = (cl+"<className>()")[scheme="java+constructor"];
      implicitContainment += <cl, defaultConstructorLOC>;
    }
  }
  
  return implicitContainment;
}

@metric{TestOverPublicMethods}
@doc{Number of JUnit tests averaged over the total number of public methods. Ideally all public methods are tested. With this number we
compute how far from the ideal situation the project is.}
@friendlyName{Number of JUnit tests averaged over the total number of public methods}
@appliesTo{java()}
@historic{}
real percentageOfTestedPublicMethods(rel[Language, loc, M3] m3s = {}) {
  m = systemM3(m3s);
  onlyTestMethods = getJUnit4TestMethods(m);
  supportTestMethods = getJUnit4SetupMethods(m);
  interfaceMethods = { meth | <entity, meth> <- m@containment, isMethod(meth), isInterface(entity) };
  allPublicMethods = { meth | meth <- m@declarations<0> - interfaceMethods - onlyTestMethods - supportTestMethods, isMethod(meth), \public() in m@modifiers[meth] };
  directlyCalledFromTestMethods = domainR(m@methodInvocation, onlyTestMethods);
  testedPublicMethods = rangeR(directlyCalledFromTestMethods + (directlyCalledFromTestMethods o m@methodOverrides<1,0>), allPublicMethods);
  return (100.0 * size(range(testedPublicMethods)))/size(allPublicMethods);
}

@metric{NumberOfTestMethods}
@doc{Number of JUnit test methods}
@friendlyName{{Number of JUnit test methods. This is an intermediate absolute metric used to compute others. The bare metric is hard to compare between projects.}
@appliesTo{java()}
@historic
int numberOfTestMethods(rel[Language, loc, M3] m3s = {}) {
  return size(getJUnit4TestMethods(systemM3(m3s)));
}


@metric{JavaUnitTestCoverage}
@doc{How well do the project's unit tests cover its code? A static approximation is done, measuring the code which would be executed if all JUnit tests are run. 
This analysis may produce a higher number than a dynamic analysis would (due to dynamic dispatch and overriding) but it indicates bad coverage easily and it follows the trend.}
@friendlyName{Java unit test coverage}
@uses{("TestOverPublicMethods": "testOverPublicMethods", "TestCoverage": "testCoverage", "TestCoverage.historic": "history")}
@appliesTo{java()}
Factoid JavaUnitTestCoverage(real testOverPublicMethods = -1.0, real testCoverage = -1.0, rel[datetime, real] history = {}) {
  sl = historicalSlope(history, 6);
                         
  expect = "";
                            
  switch (<sl < 0.1, -0.1 >= sl && sl <= 0.1, sl > 0.1>) {
    case <true   , _      , _     > : { expect = "The situation is getting worse in the last six months";  }
    case <_      , true   , _     > : { expect = "This situation is stable";  }
    case <_      , _      , true  > : { expect = "This situation is improving over the last six months";  }
  }
   
  if (testOverPublicMethods == -1.0 || testCoverage == -1.0) {
    throw undefined("Not enough test coverage data available", |tmp:///|);
  }

  stars = 1 + toInt(testCoverage / 25.0);

  if (stars > 4) {
    stars = 4;
  }

  txt = "The percentage of methods covered by unit tests is estimated at <testCoverage>%. <expect>. The estimated coverage of public methods is <testOverPublicMethods>%";
  
  return factoid(txt, starLookup[stars]); 
}
