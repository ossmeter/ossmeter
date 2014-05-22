module ClassChurn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;
import Set;

@metric{classChurn}
@doc{classChurn}
@friendlyName{classChurn}
int getClassChurn(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
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