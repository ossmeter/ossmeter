module MethodChurn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import Set;

@metric{methodChurn}
@doc{methodChurn}
@friendlyName{methodChurn}
@appliesTo{java()}
int getMethodChurn(ProjectDelta delta = \empty()) {
  int churnCount = 0;
  visit(methodChurn) {
    case unchanged(loc locator): churnCount += 0; 
    case returnTypeChanged(loc method, TypeSymbol oldType, TypeSymbol newType): churnCount += 1; 
    case signatureChanged(loc old, loc new): churnCount += 1; 
    case modifierChanged(loc method, set[Modifier] oldModifiers, set[Modifier] newModifiers): churnCount += 1;
    case deprecated(loc locator): churnCount += 1; 
    case undeprecated(loc locator): churnCount += 1;
    case added(loc locator): churnCount += 1; 
    case deleted(loc locator): churnCount += 1;
  }
  return churnCount;
}