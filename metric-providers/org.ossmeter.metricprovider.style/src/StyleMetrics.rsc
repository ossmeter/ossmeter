module StyleMetrics

import org::ossmeter::metricprovider::MetricProvider;

extend lang::java::style::StyleChecker;

import ValueIO;
import IO;
import Map;
import Set;
import List;
import DateTime;
import String;
import util::Math;
import analysis::statistics::SimpleRegression;

alias Table = rel[str group, str kind, loc file, int line];
 
@metric{styleViolations}
@doc{
  Lists a large number of categorized style violations for a project which will be categorized and
  summarized by depending metrics.    
}
@friendlyName{Style Violations}
@appliesTo{java()}
Table styleViolations(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
  messages = [*styleChecker(m3, {ast}) | <\java(), file, ast> <- asts, <\java(), file, m3> <- m3s, bprintln("processing <file>")];
  return { <group,kind,file.top, file.begin.line> | str kind(str group, loc file) <- messages};
}

@metric{filesWithBlockCheckViolations}
@doc{Percentage of the projects files with error prone use of code blocks (goto fail)}
@friendlyName{filesWithBlockCheckViolations}
@uses = ("styleViolations":"styleViolations")
@appliesTo{java()}
@historic{}
int filesWithBlockCheckViolations(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) {
   javaFiles = {l | <\java(), l, _> <- m3s};
   blockViolationFiles = { l | <"blockCheck", _, l, _> <- styleViolations};
   return percent(size(blockViolationFiles), size(javaFiles));  
}

