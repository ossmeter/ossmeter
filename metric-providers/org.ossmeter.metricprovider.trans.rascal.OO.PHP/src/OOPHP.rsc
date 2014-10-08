module OOPHP

import Prelude;

import lang::php::m3::Core;
import analysis::m3::AST;
import lang::php::ast::System;
import lang::php::ast::AbstractSyntax;

import PHP;

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

import org::ossmeter::metricprovider::MetricProvider;


@memo
private rel[loc, loc] superTypes(M3 m3) = m3@extends + m3@implements; // TODO traits?

@memo
private set[loc] allTypes(M3 m3) = classes(m3) + interfaces(m3); // TODO traits?

@memo
private rel[loc, loc] allMethods(M3 m3) = { <p, m> | <p, m> <- m3@containment, isClass(p) || isInterface(p), isMethod(m) }; // TODO traits?

@memo
private rel[loc, loc] allFields(M3 m3) = { <p, m> | <p, m> <- m3@containment, isClass(p) || isInterface(p), isField(m) }; // TODO traits?

@memo
private rel[loc, loc] overridableMethods(M3 m3) = { <p, m> | <p, m> <- allMethods(m3), \private() notin m3@modifiers[m] };

@memo
private rel[loc, loc] methodOverrides(M3 m3) {
	ancestors = superTypes(m3)+;
	overridables = overridableMethods(m3);
	return { <sm, m> | <p, m> <- allMethods(m3), a <- ancestors[p], sm <- overridables[a], sm.file == m.file };
}

@memo
private rel[loc, loc] typeDependencies(M3 m3) = typeDependencies(superTypes(m3), m3@calls, m3@accesses, {}, domainR(m3@containment+, allTypes(m3)), allTypes(m3));

@memo
private rel[loc, loc] packageTypes(M3 m3) = { <p, t> | <p, t> <- m3@containment, isNamespace(p), isClass(t) || isInterface(t) };



@metric{A-PHP}
@doc{Abstractness (PHP)}
@friendlyName{Abstractness (PHP)}
@appliesTo{php()}
real A_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	types = allTypes(m3);
	
	abstractTypes = { t | t <- types, \abstract() in m3@modifiers[t] || \abstract() in m3@modifiers[m3@containment[t]] };
	
	return A(abstractTypes, types);
}

@metric{RR-PHP}
@doc{Reuse ratio (PHP)}
@friendlyName{Reuse ratio (PHP)}
@appliesTo{php()}
real RR_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	return RR(superTypes(m3), allTypes(m3));
}

@metric{SR-PHP}
@doc{Specialization ratio (PHP)}
@friendlyName{Specialization ratio (PHP)}
@appliesTo{php()}
real SR_PHP(rel[Language, loc, M3] m3s = {}) {
	return SR(superTypes(systemM3(m3s)));
}

@metric{DIT-PHP}
@doc{Depth of inheritance tree (PHP)}
@friendlyName{Depth of inheritance tree (PHP)}
@appliesTo{php()}
map[loc, int] DIT_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	return DIT(superTypes(m3), allTypes(m3));
}

@metric{NOC-PHP}
@doc{Number of children (PHP)}
@friendlyName{Number of children (PHP)}
@appliesTo{php()}
map[loc, int] NOC_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	return NOC(superTypes(m3), allTypes(m3));
}

@metric{CBO-PHP}
@doc{Coupling between objects (PHP)}
@friendlyName{Coupling between objects (PHP)}
@appliesTo{php()}
map[loc, int] CBO_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	return CBO(typeDependencies(m3), allTypes(m3));
}


@memo
private tuple[map[loc, int], map[loc, int]] dac_mpc(rel[Language, loc, AST] asts) {
	map[loc, int] dac = ();
	map[loc, int] mpc = ();
	
	for ( <php(), _, phpAST(a)> <- asts, /c:class(_, _, _, _, _) <- a ) {
		top-down-break visit (c) {
			case new(_, _): dac[c@decl]?0 += 1;
			case methodCall(_, _, _): mpc[c@decl]?0 += 1;
			case staticCall(_, _, _): mpc[c@decl]?0 += 1;
		}
	}
	
	return <dac, mpc>;
}


@metric{DAC-PHP}
@doc{Data abstraction coupling (PHP)}
@friendlyName{Data abstraction coupling (PHP)}
@appliesTo{php()}
map[loc, int] DAC_PHP(rel[Language, loc, AST] asts = {}) {
	return dac_mpc(asts)[0];
}

@metric{MPC-PHP}
@doc{Message passing coupling (PHP)}
@friendlyName{Message passing coupling (PHP)}
@appliesTo{php()}
map[loc, int] MPC_PHP(rel[Language, loc, AST] asts = {}) {
	return dac_mpc(asts)[1];
}

@metric{CF-PHP}
@doc{Coupling factor (PHP)}
@friendlyName{Coupling factor (PHP)}
@appliesTo{php()}
real CF_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return CF(typeDependencies(m3), superTypes(m3), allTypes(m3));
}

@metric{Ca-PHP}
@doc{Afferent coupling (PHP)}
@friendlyName{Afferent coupling (PHP)}
@appliesTo{php()}
map[loc, int] Ca_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return Ca(packageTypes(m3), typeDependencies(m3));
}

@metric{Ce-PHP}
@doc{Efferent coupling (PHP)}
@friendlyName{Efferent coupling (PHP)}
@appliesTo{php()}
map[loc, int] Ce_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return Ce(packageTypes(m3), typeDependencies(m3));
}

@metric{I-PHP}
@doc{Instability (PHP)}
@friendlyName{Instability (PHP)}
@appliesTo{php()}
@uses{("Ce-PHP": "ce", "Ca-PHP": "ca")}
map[loc, real] I_PHP(map[loc, int] ce = (), map[loc, int] ca = ()) {
	packages = domain(ca) + domain(ce);

	return ( p : I(ca[p]?0, ce[p]?0) | p <- packages );
}

@metric{RFC-PHP}
@doc{Response for class (PHP)}
@friendlyName{Response for class (PHP)}
@appliesTo{php()}
map[loc, int] RFC_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return RFC(m3@calls, allMethods(m3), allTypes(m3));
}

@metric{MIF-PHP}
@doc{Method inheritance factor (PHP)}
@friendlyName{Method inheritance factor (PHP)}
@appliesTo{php()}
map[loc, real] MIF_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	inheritableMethods = { <t, m> | <t, m> <- allMethods(m3), {\private(), \abstract()} & m3@modifiers[m] == {} };
	
	return MIF(allMethods(m3), inheritableMethods, m3@extends, classes(m3));
}

@metric{AIF-PHP}
@doc{Attribute inheritance factor (PHP)}
@friendlyName{Attribute inheritance factor (PHP)}
@appliesTo{php()}
map[loc, real] AIF_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	publicAndProtectedFields = { <t, f> | <t, f> <- allFields(m3), \private() notin m3@modifiers[f] };
	
	return MIF(allFields(m3), publicAndProtectedFields, superTypes(m3), allTypes(m3));
}

@doc{
	Reuseable method for AHF and MHF
}
private real hidingFactor(M3 m3, rel[loc, loc] members) {
	set[loc] publicMembers = {};
	rel[loc, loc] protectedMembers = {};
	
	for (<t, m> <- members) {
		mods = m3@modifiers[m];
		if (\private() in mods) {
			; // ignored
		} else if (\protected() in mods) {
			protectedMembers += {<t, m>};
		} else {
			publicMembers += {m};
		}	
	}
	
	subTypes = invert(superTypes(m3))+;
	
	visible = allTypes(m3) * publicMembers // ignore namespaces etc.
			+ { <s, m> | <t, m> <- protectedMembers, s <- subTypes[t] }; 

	return MHF(members, visible, allTypes(m3));
}

@metric{MHF-PHP}
@doc{Method hiding factor (PHP)}
@friendlyName{Method hiding factor (PHP)}
@appliesTo{php()}
real MHF_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return hidingFactor(m3, allMethods(m3));
}

@metric{AHF-PHP}
@doc{Attribute hiding factor (PHP)}
@friendlyName{Attribute hiding factor (PHP)}
@appliesTo{php()}
real AHF_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return hidingFactor(m3, allFields(m3));
}

@metric{PF-PHP}
@doc{Polymorphism factor (PHP)}
@friendlyName{Polymorphism factor (PHP)}
@appliesTo{php()}
real PF_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	return PF(superTypes(m3), methodOverrides(m3), overridableMethods(m3), allTypes(m3));
}

@metric{LCOM-PHP}
@doc{Lack of cohesion in methods (PHP)}
@friendlyName{Lack of cohesion in methods (PHP)}
@appliesTo{php()}
map[loc, int] LCOM_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return LCOM(m3@accesses, allMethods(m3), allFields(m3), allTypes(m3));
}


@metric{LCOM4-PHP}
@doc{Lack of cohesion in methods 4 (PHP)}
@friendlyName{Lack of cohesion in methods 4 (PHP)}
@appliesTo{php()}
map[loc, int] LCOM_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return LCOM4(m3@calls, m3@accesses, allMethods(m3), allFields(m3), allTypes(m3));
}

@metric{TCC-PHP}
@doc{Tight class cohesion (PHP)}
@friendlyName{Tight class cohesion (PHP)}
@appliesTo{php()}
map[loc, real] TCC_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return TCC(allMethods(m3), allFields(m3), m3@calls, m3@accesses, allTypes(m3));
}

@metric{LCC-PHP}
@doc{Loose class cohesion (PHP)}
@friendlyName{Loose class cohesion (PHP)}
@appliesTo{php()}
map[loc, real] LCC_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return LCC(allMethods(m3), allFields(m3), m3@calls, m3@accesses, allTypes(m3));
}

@metric{NOM-PHP}
@doc{Number of methods (PHP)}
@friendlyName{Number of methods (PHP)}
@appliesTo{php()}
map[loc, int] NOM_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return NOM(allMethods(m3), allTypes(m3));
}

@metric{NOA-PHP}
@doc{Number of attributes (PHP)}
@friendlyName{Number of attributes (PHP)}
@appliesTo{php()}
map[loc, int] NOA_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	return NOA(allFields(m3), allTypes(m3));
}


@metric{Coupling-PHP}
@doc{PHP coupling}
@friendlyName{PHP coupling}
@appliesTo{php()}
@uses{("CBO-PHP": "cbo")}
Factoid Coupling_PHP(map[loc, int] cbo = ()) {
	return Coupling("PHP", cbo);
}


@metric{Cohesion-PHP}
@doc{PHP cohesion}
@friendlyName{PHP cohesion}
@appliesTo{php()}
@uses{("LCOM4-PHP": "lcom4")}
Factoid Cohesion_PHP(map[loc, int] lcom4 = ()) {
	return Cohesion("PHP", lcom4);
}
