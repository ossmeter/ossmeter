module ActiveCommitters

import org::ossmeter::metricprovider::MetricProvider;

import ValueIO;
import IO;
import Map;
import Set;
import List;
import DateTime;
import String;
import util::Math;

@metric{committersToday}
@doc{activeCommitters}
@friendlyName{activeCommitters}
@appliesTo{generic()}
set[str] committersToday(ProjectDelta delta = \empty()) {
  return {co.author | /VcsCommit co := delta};
}

@metric{activeCommitters}
@doc{Committers who have been active the last two weeks}
@friendlyName{committersLastTwoWeeks}
@uses{("org.ossmeter.metricprovider.trans.rascal.activecommitters.committersToday":"committersToday")}
@appliesTo{generic()}
rel[datetime, set[str]] activeCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, committersToday>};  
}


@metric{numberOfActiveCommitters}
@doc{Number of active committers over time}
@friendlyName{numberOfActiveCommitters}
@uses{("org.ossmeter.metricprovider.trans.rascal.activecommitters.activeCommitters" :"activeCommitters")}
@appliesTo{generic()}
@historic{}
int numberOfActiveCommitters(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});

@metric{maximumActiveCommittersEver}
@doc{What is the maximum number of committers which have been active together in any two week period?}
@friendlyName{maximumActiveCommittersEver}
@uses{("org.ossmeter.metricprovider.trans.rascal.activecommitters.numberOfActiveCommitters.historic" :"history")}
@appliesTo{generic()}
int maximumActiveCommittersEver(rel[datetime d, int n] history = {}) {
  return max(history<n>);
}

@metric{programmerCommunity}
@doc{based on committer activity, what is the health of the community?}
@friendlyName{community of programmers}
@appliesTo{generic()}
Factoid programmerCommunity() {
  return factoid("This is a test", four());
}

