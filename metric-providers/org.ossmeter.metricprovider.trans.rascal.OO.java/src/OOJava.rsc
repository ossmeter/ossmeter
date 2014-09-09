module OOJava

import lang::java::m3::Core;

@metric{A-Java}
@doc{Abstractness (Java)}
@friendlyName{Abstractness (Java)}
@appliesTo{java()}
real A_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{RR-Java}
@doc{Reuse ratio (Java)}
@friendlyName{Reuse ratio (Java)}
@appliesTo{java()}
real RR_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{SR-Java}
@doc{Specialization ratio (Java)}
@friendlyName{Specialization ratio (Java)}
@appliesTo{java()}
real SR_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{DIT-Java}
@doc{Depth of inheritance tree (Java)}
@friendlyName{Depth of inheritance tree (Java)}
@appliesTo{java()}
real DIT_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{NOC-Java}
@doc{Number of children (Java)}
@friendlyName{Number of children (Java)}
@appliesTo{java()}
real NOC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{CBO-Java}
@doc{Coupling between objects (Java)}
@friendlyName{Coupling between objects (Java)}
@appliesTo{java()}
real CBO_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{DAC-Java}
@doc{Data abstraction coupling (Java)}
@friendlyName{Data abstraction coupling (Java)}
@appliesTo{java()}
real DAC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{MPC-Java}
@doc{Message passing coupling (Java)}
@friendlyName{Message passing coupling (Java)}
@appliesTo{java()}
real MPC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{CF-Java}
@doc{Coupling factor (Java)}
@friendlyName{Coupling factor (Java)}
@appliesTo{java()}
real CF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{Ca-Java}
@doc{Afferent coupling (Java)}
@friendlyName{Afferent coupling (Java)}
@appliesTo{java()}
real Ca_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{Ce-Java}
@doc{Efferent coupling (Java)}
@friendlyName{Efferent coupling (Java)}
@appliesTo{java()}
real Ce_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{I-Java}
@doc{Instability (Java)}
@friendlyName{Instability (Java)}
@appliesTo{java()}
real I_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{RFC-Java}
@doc{Response for class (Java)}
@friendlyName{Response for class (Java)}
@appliesTo{java()}
real RFC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{MIF-Java}
@doc{Method inheritance factor (Java)}
@friendlyName{Method inheritance factor (Java)}
@appliesTo{java()}
map[loc, real] MIF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{AIF-Java}
@doc{Attribute inheritance factor (Java)}
@friendlyName{Attribute inheritance factor (Java)}
@appliesTo{java()}
map[loc, real] AIF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{MHF-Java}
@doc{Method hiding factor (Java)}
@friendlyName{Method hiding factor (Java)}
@appliesTo{java()}
real MHF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{AHF-Java}
@doc{Attribute hiding factor (Java)}
@friendlyName{Attribute hiding factor (Java)}
@appliesTo{java()}
real AHF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{PF-Java}
@doc{Polymorphism factor (Java)}
@friendlyName{Polymorphism factor (Java)}
@appliesTo{java()}
real PF_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{LCOM-Java}
@doc{Lack of cohesion in methods (Java)}
@friendlyName{Lack of cohesion in methods (Java)}
@appliesTo{java()}
real LCOM_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{TCC-Java}
@doc{Tight class cohesion (Java)}
@friendlyName{Tight class cohesion (Java)}
@appliesTo{java()}
real TCC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}

@metric{LCC-Java}
@doc{Loose class cohesion (Java)}
@friendlyName{Loose class cohesion (Java)}
@appliesTo{java()}
real LCC_Java(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
	return 0.0;
}


@metric{NOM-Java}
@doc{Number of methods (Java)}
@friendlyName{Number of methods (Java)}
@appliesTo{java()}
map[loc, int] NOM_Java(rel[Language, loc, M3] m3s = {}) {
	return ();
}

@metric{NOA-Java}
@doc{Number of attributes (Java)}
@friendlyName{Number of attributes (Java)}
@appliesTo{java()}
map[loc, int] NOA_Java(rel[Language, loc, M3] m3s = {}) {
	return ();
}
