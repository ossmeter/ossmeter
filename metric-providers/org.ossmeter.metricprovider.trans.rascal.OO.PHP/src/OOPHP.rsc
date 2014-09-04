module OOPHP

import lang::php::m3::Core;
import lang::php::ast::System;

@metric{A-PHP}
@doc{Abstractness (PHP)}
@friendlyName{Abstractness (PHP)}
@appliesTo{php()}
num A_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{RR-PHP}
@doc{Reuse ratio (PHP)}
@friendlyName{Reuse ratio (PHP)}
@appliesTo{php()}
num RR_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{SR-PHP}
@doc{Specialization ratio (PHP)}
@friendlyName{Specialization ratio (PHP)}
@appliesTo{php()}
num SR_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{DIT-PHP}
@doc{Depth of inheritance tree (PHP)}
@friendlyName{Depth of inheritance tree (PHP)}
@appliesTo{php()}
num DIT_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{NOC-PHP}
@doc{Number of children (PHP)}
@friendlyName{Number of children (PHP)}
@appliesTo{php()}
num NOC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{CBO-PHP}
@doc{Coupling between objects (PHP)}
@friendlyName{Coupling between objects (PHP)}
@appliesTo{php()}
num CBO_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{DAC-PHP}
@doc{Data abstraction coupling (PHP)}
@friendlyName{Data abstraction coupling (PHP)}
@appliesTo{php()}
num DAC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{MPC-PHP}
@doc{Message passing coupling (PHP)}
@friendlyName{Message passing coupling (PHP)}
@appliesTo{php()}
num MPC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{CF-PHP}
@doc{Coupling factor (PHP)}
@friendlyName{Coupling factor (PHP)}
@appliesTo{php()}
num CF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{Ca-PHP}
@doc{Afferent coupling (PHP)}
@friendlyName{Afferent coupling (PHP)}
@appliesTo{php()}
num Ca_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{Ce-PHP}
@doc{Efferent coupling (PHP)}
@friendlyName{Efferent coupling (PHP)}
@appliesTo{php()}
num Ce_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{I-PHP}
@doc{Instability (PHP)}
@friendlyName{Instability (PHP)}
@appliesTo{php()}
num I_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{RFC-PHP}
@doc{Response for class (PHP)}
@friendlyName{Response for class (PHP)}
@appliesTo{php()}
num RFC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{MIF-PHP}
@doc{Method inheritance factor (PHP)}
@friendlyName{Method inheritance factor (PHP)}
@appliesTo{php()}
num MIF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{AIF-PHP}
@doc{Attribute inheritance factor (PHP)}
@friendlyName{Attribute inheritance factor (PHP)}
@appliesTo{php()}
num AIF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{MHF-PHP}
@doc{Method hiding factor (PHP)}
@friendlyName{Method hiding factor (PHP)}
@appliesTo{php()}
num MHF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{AHF-PHP}
@doc{Attribute hiding factor (PHP)}
@friendlyName{Attribute hiding factor (PHP)}
@appliesTo{php()}
num AHF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{PF-PHP}
@doc{Polymorphism factor (PHP)}
@friendlyName{Polymorphism factor (PHP)}
@appliesTo{php()}
num PF_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{LCOM-PHP}
@doc{Lack of cohesion in methods (PHP)}
@friendlyName{Lack of cohesion in methods (PHP)}
@appliesTo{php()}
num LCOM_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{TCC-PHP}
@doc{Tight class cohesion (PHP)}
@friendlyName{Tight class cohesion (PHP)}
@appliesTo{php()}
num TCC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}

@metric{LCC-PHP}
@doc{Loose class cohesion (PHP)}
@friendlyName{Loose class cohesion (PHP)}
@appliesTo{php()}
num LCC_PHP(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
}
