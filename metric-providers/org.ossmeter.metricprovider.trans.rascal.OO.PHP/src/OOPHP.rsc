module OOPHP

import Prelude;

import lang::php::m3::Core;
import analysis::m3::AST;
import lang::php::ast::System;

import OO;
import ck::NOC;
import ck::DIT;

@memo
private M3 systemM3(rel[Language, loc, M3] m3s) {
	return composeM3(|tmp:///|, range(m3s[php()]));
}

@metric{A-PHP}
@doc{Abstractness (PHP)}
@friendlyName{Abstractness (PHP)}
@appliesTo{php()}
real A_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{RR-PHP}
@doc{Reuse ratio (PHP)}
@friendlyName{Reuse ratio (PHP)}
@appliesTo{php()}
real RR_PHP(rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	return RR(m3@extends + m3@implements, classes(m3) + interfaces(m3)); // Traits?
}

@metric{SR-PHP}
@doc{Specialization ratio (PHP)}
@friendlyName{Specialization ratio (PHP)}
@appliesTo{php()}
real SR_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);

	return SR(m3@extends + m3@implements); // Traits?
}

@metric{DIT-PHP}
@doc{Depth of inheritance tree (PHP)}
@friendlyName{Depth of inheritance tree (PHP)}
@appliesTo{php()}
map[loc, int] DIT_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	return DIT(m3@extends + m3@implements, classes(m3) + interfaces(m3)); // traits?
}

@metric{NOC-PHP}
@doc{Number of children (PHP)}
@friendlyName{Number of children (PHP)}
@appliesTo{php()}
map[loc, int] NOC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	M3 m3 = systemM3(m3s);
	
	return NOC(m3@extends + m3@implements, classes(m3) + interfaces(m3)); // traits?
}

@metric{CBO-PHP}
@doc{Coupling between objects (PHP)}
@friendlyName{Coupling between objects (PHP)}
@appliesTo{php()}
real CBO_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{DAC-PHP}
@doc{Data abstraction coupling (PHP)}
@friendlyName{Data abstraction coupling (PHP)}
@appliesTo{php()}
real DAC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{MPC-PHP}
@doc{Message passing coupling (PHP)}
@friendlyName{Message passing coupling (PHP)}
@appliesTo{php()}
real MPC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{CF-PHP}
@doc{Coupling factor (PHP)}
@friendlyName{Coupling factor (PHP)}
@appliesTo{php()}
real CF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{Ca-PHP}
@doc{Afferent coupling (PHP)}
@friendlyName{Afferent coupling (PHP)}
@appliesTo{php()}
real Ca_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{Ce-PHP}
@doc{Efferent coupling (PHP)}
@friendlyName{Efferent coupling (PHP)}
@appliesTo{php()}
real Ce_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{I-PHP}
@doc{Instability (PHP)}
@friendlyName{Instability (PHP)}
@appliesTo{php()}
real I_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{RFC-PHP}
@doc{Response for class (PHP)}
@friendlyName{Response for class (PHP)}
@appliesTo{php()}
real RFC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{MIF-PHP}
@doc{Method inheritance factor (PHP)}
@friendlyName{Method inheritance factor (PHP)}
@appliesTo{php()}
real MIF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{AIF-PHP}
@doc{Attribute inheritance factor (PHP)}
@friendlyName{Attribute inheritance factor (PHP)}
@appliesTo{php()}
real AIF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{MHF-PHP}
@doc{Method hiding factor (PHP)}
@friendlyName{Method hiding factor (PHP)}
@appliesTo{php()}
real MHF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{AHF-PHP}
@doc{Attribute hiding factor (PHP)}
@friendlyName{Attribute hiding factor (PHP)}
@appliesTo{php()}
real AHF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{PF-PHP}
@doc{Polymorphism factor (PHP)}
@friendlyName{Polymorphism factor (PHP)}
@appliesTo{php()}
real PF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{LCOM-PHP}
@doc{Lack of cohesion in methods (PHP)}
@friendlyName{Lack of cohesion in methods (PHP)}
@appliesTo{php()}
real LCOM_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{TCC-PHP}
@doc{Tight class cohesion (PHP)}
@friendlyName{Tight class cohesion (PHP)}
@appliesTo{php()}
real TCC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{LCC-PHP}
@doc{Loose class cohesion (PHP)}
@friendlyName{Loose class cohesion (PHP)}
@appliesTo{php()}
real LCC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}
