module ActiveCommitters

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import ValueIO;
import IO;
import Map;
import Set;
import List;
import DateTime;
import String;

@metric{activeCommitters}
@doc{activeCommitters}
@friendlyName{activeCommitters}
str activeCommitters(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  list[str] activeAuthors = [];
  datetime today = delta.date;
  writeBinaryValueFile(|home:///ossmeter/<delta.project.name>/activecommitters_<printDate(today.justDate, "yyyy_mm_dd")>.am3|, [ commit.author | /VcsCommit commit <- delta ]);
  list[datetime] activePeriod = dateRangeByDay(createInterval(decrementDays(delta.date, 15), today));
  
  for (datetime d <- activePeriod) {
    loc activeCommittersForDay = |home:///ossmeter/<delta.project.name>/activecommitters_<printDate(d.justDate, "yyyy_mm_dd")>.am3|;
    
    if (exists(activeCommittersForDay)) {
      activeAuthors += readBinaryValueFile(#list[str], activeCommittersForDay);
    }
  }
  
  map[str, int] dist = distribution(activeAuthors);
  
  list[int] activityCount = reverse(sort(range(dist)));
  map[int, set[str]] comparator = invert(dist);
  
  return intercalate(", ", [author | numActivity <- activityCount, author <- comparator[numActivity]]);
}

@metric{numberofactivecommitters}
@doc{numbrofactivecommitters}
@friendlyName{numberofactivecommitters}
@uses{org.ossmeter.metricprovider.activecommitters.activeCommitters}
int numberOfActiveCommitters(str metricData) {
  return size(split(",", metricData));
}
