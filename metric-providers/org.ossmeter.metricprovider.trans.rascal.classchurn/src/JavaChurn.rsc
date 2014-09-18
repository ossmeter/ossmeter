module JavaChurn

import org::ossmeter::metricprovider::ProjectDelta;
import Set;

@metric{classChurn}
@doc{classChurn}
@friendlyName{classChurn}
@appliesTo{java()}
int getClassChurn(ProjectDelta delta = \empty()) {
  int churnCount = 0;
  visit(classChurn) {
    case classContentChanged(loc changedClass, set[loc] changedContent): churnCount += size(changedContent);
    case classModifierChanged(loc locator, set [Modifier] oldModifiers, set [Modifier] newModifiers): churnCount += 1;
    case classDeprecated(loc locator): churnCount += 1;
    case classUndeprecated(loc locator): churnCount += 1;
    case addedClass(loc locator): churnCount += 1;
    case deletedClass(loc locator): churnCount += 1;
  }
  return churnCount;
}

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