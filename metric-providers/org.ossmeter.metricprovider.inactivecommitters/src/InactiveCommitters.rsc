module InactiveCommitters

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import ValueIO;
import IO;
import Map;
import Set;
import List;
import DateTime;

@metric{inactiveCommitters}
@doc{inactiveCommitters}
@friendlyName{inactiveCommitters}
list[str] inactiveCommitters(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  set[str] threeMonthsActive = {};
  set[str] activeBeforeThreeMonths = {};
  datetime today = delta.date;
  writeBinaryValueFile(|home:///ossmeter/<delta.project.name>/activecommitters_<printDate(today.justDate, "YYYYMMdd")>.am3|, [ commit.author | /VcsCommit commit <- delta ]);
  datetime threeMonthsAgo = decrementMonths(delta.date, 3);
  list[datetime] activePeriod = dateRangeByDay(createInterval(threeMonthsAgo, today));
  
  for (datetime d <- activePeriod) {
    loc activeCommittersForDay = |home:///ossmeter/<delta.project.name>/activecommitters_<printDate(d.justDate, "YYYYMMdd")>.am3|;
    
    if (exists(activeCommittersForDay)) {
      threeMonthsActive += { *readBinaryValueFile(#list[str], activeCommittersForDay) };
    }
  }
  
  activePeriod = dateRangeByDay(createInterval(decrementMonths(threeMonthsAgo, 6), threeMonthsAgo));
  
  for (datetime d <- activePeriod) {
    loc activeCommittersForDay = |home:///ossmeter/<delta.project.name>/activecommitters_<printDate(d.justDate, "YYYYMMdd")>.am3|;
    
    if (exists(activeCommittersForDay)) {
      activeBeforeThreeMonths += { *readBinaryValueFile(#list[str], activeCommittersForDay) };
    }
  }
  
  return [*(activeBeforeThreeMonths - threeMonthsActive)];
}
