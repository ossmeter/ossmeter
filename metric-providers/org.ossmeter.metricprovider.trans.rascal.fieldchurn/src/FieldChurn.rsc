module FieldChurn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{fieldChurn}
@doc{fieldChurn}
@friendlyName{fieldChurn}
@appliesTo{java()}
int getFieldChurn(ProjectDelta delta = \empty()) {
  int churnCount = 0;
  visit(fieldChurn) {
    case fieldModifierChanged(loc locator, set [Modifier] oldModifiers, set [Modifier] newModifiers): churnCount += 1;
    case fieldTypeChanged(loc locator, _, _): churnCount += 1;
    case fieldDeprecated (loc locator): churnCount += 1;
    case fieldUndeprecated(loc locator): churnCount += 1;
    case addedField(loc locator): churnCount += 1;   
    case deletedField(loc locator): churnCount += 1;
  }
  return churnCount;
}