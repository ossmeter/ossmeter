module WeekendActivity

import org::ossmeter::metricprovider::MetricProvider;
import DateTime;
import IO;

@metric{numberOfWeekendCommits}
@doc{Number of commits during the weekend}
@friendlyName{numberOfWeekendCommits}
@appliesTo{generic()}
@historic
int numberOfWeekendCommits(ProjectDelta delta = \empty()) {
  str dayOfWeek = printDate(delta.date, "EEE");
  if (dayOfWeek == "Sat" || dayOfWeek == "Sun") {
    return (0 | it + 1 | /VcsCommit vcsCommit <- delta);
  }
  throw undefined("<delta.date> is not a weekend", |metric:///numberOfWeekendCommits|);
}
