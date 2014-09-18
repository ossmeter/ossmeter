module Main

import Set;
import Relation;
import IO;
import util::ValueUI;
import analysis::graphs::Graph;
extend lang::java::m3::Core;
import JUnit4;
import Java;


@metric{TestCoverage}
@doc{Static Estimation of test coverage}
@friendlyName{Static Estimation of test coverage}
@appliesTo{java()}
@memo
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
      for (method <- fullContainment[caller], method.scheme == "java+constructor") {
        liftedInvocations += { <method, callee> };
      }
    }
  }
  
  fullCallGraph = liftedInvocations + implicitCalls + m@methodOverrides<1,0>;
  allTestMethods = getJUnit4TestMethods(m) + getJUnit4SetupMethods(m);
  interfaceMethods = { method | <entity, method> <- m@containment, isMethod(method), isInterface(entity) };
  set[loc] reachableMethods = { method | method <- reach(fullCallGraph, allTestMethods), method in m@declarations<0> } - allTestMethods - interfaceMethods;
  int totalDefinedMethods = (0 | it + 1 | method <- m@declarations<0> - allTestMethods - interfaceMethods, isMethod(method));
  return (100.0 * size(reachableMethods)) / totalDefinedMethods;
}

/*  
 * Adding implicit calls between a constructor and its super
 */
private rel[loc, loc] getImplicitCalls(M3 m, rel[loc, loc] implicitContainment) {
  fullContainment = m@containment + implicitContainment;  
  
  rel[loc, loc] implicitCalls = {};
  
  for (<child, parent> <- m@extends) {
    if (parent in implicitContainment<0>) {
      for (constructor <- fullContainment[child], constructor.scheme == "java+constructor") {
        assert(size(implicitContainment[parent]) == 1) : "Found more than one implicit constuctor";
        implicitCalls += <constructor, getOneFrom(implicitContainment[parent])>;
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
  
  for (class <- m@declarations<0>, class.scheme == "java+class" || class.scheme == "java+anonymousClass" || class.scheme == "java+enum") {
    allMethods = { candidate | candidate <- m@containment[class], isMethod(candidate) };
    
    if (!any(method <- allMethods, method.scheme == "java+constructor")) {
      possibleNames = m@names<1,0>[class];
      assert(size(possibleNames) <= 1) : "Found more than one simple name entry for a qualified name";
      className = isEmpty(possibleNames) ? "" : getOneFrom(possibleNames);
      defaultConstructorLOC = (class+"<className>()")[scheme="java+constructor"];
      implicitContainment += <class, defaultConstructorLOC>;
    }
  }
  
  return implicitContainment;
}

@metric{TestOverPublicMethods}
@doc{Number of JUnit tests averaged over the total number of public methods}
@friendlyName{Number of JUnit tests averaged over the total number of public methods}
@appliesTo{java()}
real percentageOfTestedPublicMethods(rel[Language, loc, M3] m3s = {}) {
  m = systemM3(m3s);
  onlyTestMethods = getJUnit4TestMethods(m);
  supportTestMethods = getJUnit4SetupMethods(m);
  interfaceMethods = { method | <entity, method> <- m@containment, isMethod(method), isInterface(entity) };
  allPublicMethods = { method | method <- m@declarations<0> - interfaceMethods - onlyTestMethods - supportTestMethods, isMethod(method), \public() in m@modifiers[method] };
  directlyCalledFromTestMethods = domainR(m@methodInvocation, onlyTestMethods);
  testedPublicMethods = rangeR(directlyCalledFromTestMethods + (directlyCalledFromTestMethods o m@methodOverrides<1,0>), allPublicMethods);
  return (100.0 * size(range(testedPublicMethods)))/size(allPublicMethods);
}