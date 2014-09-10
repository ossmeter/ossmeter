module OOJava

import Relation;
import Map;

import lang::java::m3::Core;
import lang::java::m3::AST;
import lang::java::m3::TypeSymbol;
import analysis::graphs::Graph;
import org::ossmeter::metricprovider::MetricProvider;

import OO;
import OOFactoids;
import ck::NOC;
import ck::DIT;
import ck::RFC;
import ck::CBO;
import ck::LCOM;
import ck::NOM;
import ck::NOA;
import mood::PF;
import mood::MHF;
import mood::MIF;


@memo
private M3 systemM3(rel[Language, loc, M3] m3s) {
  return composeM3(|java+tmp:///|, range(m3s[java()]));
}

@memo
set[loc] enums(M3 m) = { e | e <- m@declarations<name>, e.scheme == "java+enum" };

@memo
set[loc] anonymousClasses(M3 m) = { e | e <- m@declarations<name>, e.scheme == "java+anonymousClass" };

@memo
private set[loc] allTypes(M3 m) = classes(m) + interfaces(m) + enums(m) + anonymousClasses(m);

@memo
private rel[loc, loc] superTypes(M3 m) = m@extends + m@implements;

@memo
private rel[loc, loc] typeDependencies(M3 m3) = typeDependencies(superTypes(m3), m3@methodInvocation, m3@fieldAccess, typeSymbolsToTypes(m3@types), domainR(m3@containment+, allTypes(m3)), allTypes(m3));

@memo
private rel[loc, loc] allMethods(M3 m3) = { <t, m> | t <- allTypes(m3), m <- m3@containment[t], isMethod(m) };

@memo
private rel[loc, loc] allFields(M3 m3) = { <t, f> | t <- allTypes(m3), f <- m3@containment[t], isField(f) };

@memo
private rel[loc, loc] methodFieldAccesses(M3 m) = domainR(m@fieldAccess, methods(m));

@memo
private rel[loc, loc] methodMethodCalls(M3 m) = domainR(m@methodInvocation, methods(m));

@memo
private rel[loc, loc] packageTypes(M3 m3) = { <p, t> | <p, t> <- m3@containment, isPackage(p), isClass(t) || isInterface(t) || t.scheme == "java+enum" };

@metric{A-Java}
@doc{Abstractness (Java)}
@friendlyName{Abstractness (Java)}
@appliesTo{java()}
real A_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  
  types = allTypes(m3s);
  
  abstractTypes = { t | t <- types, \abstract() in m3@modifiers[t] };
  
  return A(abstractTypes, types);
}

@metric{RR-Java}
@doc{Reuse ratio (Java)}
@friendlyName{Reuse ratio (Java)}
@appliesTo{java()}
real RR_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

  return RR(superTypes(m3), allTypes(m3));
}

@metric{SR-Java}
@doc{Specialization ratio (Java)}
@friendlyName{Specialization ratio (Java)}
@appliesTo{java()}
real SR_Java(rel[Language, loc, M3] m3s = {}) {
	return SR(superTypes(systemM3(m3s)));
}

@metric{DIT-Java}
@doc{Depth of inheritance tree (Java)}
@friendlyName{Depth of inheritance tree (Java)}
@appliesTo{java()}
map[loc, int] DIT_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  
  return DIT(superTypes(m3), allTypes(m3));
}

@metric{NOC-Java}
@doc{Number of children (Java)}
@friendlyName{Number of children (Java)}
@appliesTo{java()}
map[loc, int] NOC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  
  return NOC(superTypes(m3), allTypes(m3));
}

private rel[loc, loc] typeSymbolsToTypes(rel[loc, TypeSymbol] typs) {
  rel[loc, loc] result = {};
  visit (typs) {
    case <loc entity, \class(loc decl, _)>: {
      result += { <entity, decl> };
    }
    case <loc entity, \interface(loc decl, _)> : {
      result += { <entity, decl> };
    }
    case <loc entity, \enum(loc decl)> : {
      result += { <entity, decl> };
    }
    case <loc entity, \method(_, _, TypeSymbol returnType, _)> : {
      result += typeSymbolsToTypes({<entity, returnType>});
    }
    case <loc entity, \object()> : {
      result +=  {<entity,  |java+class:///java/lang/Object|>};
    }
  }
  return result;
}

@metric{CBO-Java}
@doc{Coupling between objects (Java)}
@friendlyName{Coupling between objects (Java)}
@appliesTo{java()}
map[loc, int] CBO_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	return CBO(typeDependencies(m3), allTypes(m3));
}

// DAC for java is also measured in lang::java::style::Metrics

@metric{DAC-Java}
@doc{Data abstraction coupling (Java)}
@friendlyName{Data abstraction coupling (Java)}
@appliesTo{java()}
map[loc, int] DAC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
  map[loc, int] dac = ();
  
  for ( /c:class(_, _, _, _, _) <- asts ) { // TODO newObject() ??
    dac[c@decl] = ( 0 | it + 1 | /constructorCall(_, _) <- c) +   // TODO rewrite to visit
                  ( 0 | it + 1 | /constructorCall(_, _, _) <- c);           
  }
  
  return dac;
}

@metric{MPC-Java}
@doc{Message passing coupling (Java)}
@friendlyName{Message passing coupling (Java)}
@appliesTo{java()}
map[loc, int] MPC_Java(rel[Language, loc, AST] asts = {}) {
  map[loc, int] mpc = ();
  
  for ( /c:class(_, _, _, _, _) <- asts ) { // TODO rewrite to visit
    mpc[c@decl] = ( 0 | it + 1 | /methodCall(_, _, _) <- c ) +
                  ( 0 | it + 1 | /methodCall(_, _, _, _) <- c ) +
                  ( 0 | it + 1 | /constructorCall(_, _) <- c) + // TODO do we need to include constructorCall?
                  ( 0 | it + 1 | /constructorCall(_, _, _) <- c);
  }
  
  return mpc;
}

@metric{CF-Java}
@doc{Coupling factor (Java)}
@friendlyName{Coupling factor (Java)}
@appliesTo{java()}
real CF_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  return CF(typeDependencies(m3), superTypes(m3), allTypes(m3));
}

@metric{Ca-Java}
@doc{Afferent coupling (Java)}
@friendlyName{Afferent coupling (Java)}
@appliesTo{java()}
map[loc, int] Ca_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  return Ca(packageTypes(m3), typeDependencies(m3));
}

@metric{Ce-Java}
@doc{Efferent coupling (Java)}
@friendlyName{Efferent coupling (Java)}
@appliesTo{java()}
map[loc, int] Ce_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  return Ce(packageTypes(m3), typeDependencies(m3));
}

@metric{I-Java}
@doc{Instability (Java)}
@friendlyName{Instability (Java)}
@appliesTo{java()}
@uses = ("Ce-Java" : "ce", "Ca-Java" : "ca")
map[loc, real] I_Java(map[loc, int] ce = (), map[loc, int] ca = ()) {
  set[loc] packages = domain(ca) + domain(ce);

  return ( p : I(ca[p]?0, ce[p]?0) | p <- packages );
}

@metric{RFC-Java}
@doc{Response for class (Java)}
@friendlyName{Response for class (Java)}
@appliesTo{java()}
map[loc, int] RFC_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
  return RFC(m3@methodInvocation, allMethods(m3), allTypes(m3));
}

@metric{MIF-Java}
@doc{Method inheritance factor (Java)}
@friendlyName{Method inheritance factor (Java)}
@appliesTo{java()}
map[loc, real] MIF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	// TODO package visibility?	
	inheritableMethods = { <t, m> | <t, m> <- allMethods(m3), {\private(), \abstract()} & m3@modifiers[m] == {} };
	
	return MIF(allMethods(m3), inheritableMethods, m3@extends, classes(m3));
}

@metric{AIF-Java}
@doc{Attribute inheritance factor (Java)}
@friendlyName{Attribute inheritance factor (Java)}
@appliesTo{java()}
map[loc, real] AIF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	// TODO package visibility?	
	publicAndProtectedFields = { <t, f> | <t, f> <- allFields(m3), \private() notin m3@modifiers[f] };
	
	return MIF(allFields(m3), publicAndProtectedFields, superTypes(m3), allTypes(m3));
}

@doc{
	Reuseable method for AHF and MHF
}
private real hidingFactor(M3 m3, rel[loc, loc] members) {
	set[loc] publicMembers = {};
	rel[loc, loc] protectedMembers = {};
	rel[loc, loc] packageVisibleMembers = {};
	
	for (<t, m> <- members) {
		mods = m3@modifiers[m];
		if (\private() in mods) {
			; // ignored
		} else if (\protected() in mods) {
			protectedMembers += {<t, m>};
		} else if (\public() in mods) {
			publicMembers += {m};
		} else {
			packageVisibleMembers += {<t, m>};
		}
	}
	
	subTypes = invert(superTypes(m3))+;

	packageTypes = rangeR(domainR(m3@containment, packages(m3)), allTypes(m3));
	packageFriends = invert(packageTypes) o packageTypes;
	
	visible = allTypes(m3) * publicMembers
			+ { <s, m> | <t, m> <- protectedMembers, s <- subTypes[t] }
			+ { <f, m> | <t, m> <- packageVisibleMembers, f <- packageFriends[t] };

	return MHF(members, visible, allTypes(m3));
}

@metric{MHF-Java}
@doc{Method hiding factor (Java)}
@friendlyName{Method hiding factor (Java)}
@appliesTo{java()}
real MHF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return hidingFactor(m3, allMethods(m3));
}

@metric{AHF-Java}
@doc{Attribute hiding factor (Java)}
@friendlyName{Attribute hiding factor (Java)}
@appliesTo{java()}
real AHF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return hidingFactor(m3, allFields(m3));
}

@metric{PF-Java}
@doc{Polymorphism factor (Java)}
@friendlyName{Polymorphism factor (Java)}
@appliesTo{java()}
real PF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	return PF(superTypes(m3), m3@methodOverrides, overridableMethods(m3), allTypes(m3));
}

@metric{LCOM-Java}
@doc{Lack of cohesion in methods (Java)}
@friendlyName{Lack of cohesion in methods (Java)}
@appliesTo{java()}
map[loc, int] LCOM_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return LCOM(methodFieldAccesses(m3), allMethods(m3), allFields(m3), allTypes(m3));
}

@metric{TCC-Java}
@doc{Tight class cohesion (Java)}
@friendlyName{Tight class cohesion (Java)}
@appliesTo{java()}
map[loc, real] TCC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return TCC(allMethods(m3), allFields(m3), methodMethodCalls(m3), methodFieldAccesses(m3), allTypes(m3));
}

@metric{LCC-Java}
@doc{Loose class cohesion (Java)}
@friendlyName{Loose class cohesion (Java)}
@appliesTo{java()}
map[loc, real] LCC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return LCC(allMethods(m3), allFields(m3), methodMethodCalls(m3), methodFieldAccesses(m3), allTypes(m3));
}


@metric{NOM-Java}
@doc{Number of methods (Java)}
@friendlyName{Number of methods (Java)}
@appliesTo{java()}
map[loc, int] NOM_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return NOA(allMethods(m3), allTypes(m3));
}

@metric{NOA-Java}
@doc{Number of attributes (Java)}
@friendlyName{Number of attributes (Java)}
@appliesTo{java()}
map[loc, int] NOA_Java(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return NOA(allFields(m3), allTypes(m3));
}


@metric{Complexity-Java}
@doc{Java code complexity}
@friendlyName{Java code complexity}
@appliesTo{java()}
@uses{(
	"A-Java": "a",
	"RR-Java": "rr",
	"SR-Java": "sr",
	"DIT-Java": "dit",
	"NOC-Java": "noc",
	"NOA-Java": "noa",
	"NOM-Java": "nom",
	"MIF-Java": "mif",
	"AIF-Java": "aif",
	"MHF-Java": "mhf",
	"AHF-Java": "ahf",
	"PF-Java": "pf")}
Factoid Complexity_Java(
	real a = 0.0,
	real rr = 0.0,
	real sr = 0.0,
	map[loc, int] dit = (),
	map[loc, int] noc = (),
	map[loc, int] noa = (),
	map[loc, int] nom = (),
	map[loc, real] mif = (),
	map[loc, real] aif = (),
	real mhf = 0.0,
	real ahf = 0.0,
	real pf = 0.0
) {
	return Complexity("Java", a, rr, sr, dit, noc, noa, nom, mif, aif, mhf, ahf, pf);
}


@metric{Coupling-Java}
@doc{Java coupling}
@friendlyName{Java coupling}
@appliesTo{java()}
@uses{(
	"CBO-Java": "cbo",
	"DAC-Java": "dac",
	"MPC-Java": "mpc",
	"CF-Java": "cf",
	"Ce-Java": "ce",
	"Ca-Java": "ca",
	"I-Java": "i",
	"RFC-Java": "rfc")}
Factoid Coupling_Java(
	map[loc, int] cbo = (),
	map[loc, int] dac = (),
	map[loc, int] mpc = (),
	real cf = 0.0,
	map[loc, int] ce = (),
	map[loc, int] ca = (),
	map[loc, real] i = (),
	map[loc, int] rfc = ()
) {
	return Coupling("Java", cbo, dac, mpc, cf, ce, ca, i, rfc);
}


@metric{Cohesion-Java}
@doc{Java cohesion}
@friendlyName{Java cohesion}
@appliesTo{java()}
@uses{(
	"LCOM-Java": "lcom",
	"TCC-Java": "tcc",
	"LCC-Java": "lcc")}
Factoid Cohesion_Java(
	map[loc, int] lcom = (),
	map[loc, real] tcc = (),
	map[loc, real] lcc = ()
) {
	return Cohesion("Java", lcom, tcc, lcc);
}
