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
import analysis::statistics::SimpleRegression;
 
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
@uses{("committersToday":"committersToday")}
@appliesTo{generic()}
rel[datetime, set[str]] activeCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, committersToday>};  
}

@metric{longerTermActiveCommitters}
@doc{Committers who have been active the last 6 months}
@friendlyName{committersLastTwoWeeks}
@uses{("committersToday":"committersToday")}
@appliesTo{generic()}
rel[datetime, set[str]] longerTermActiveCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  sixmonths = decrementMonths(today, 6);
  return {<d,t> | <d,t> <- prev, d > sixmonths} + {<today, committersToday>};  
}


@metric{numberOfActiveCommitters}
@doc{Number of active committers over time}
@friendlyName{numberOfActiveCommitters}
@uses{("activeCommitters" :"activeCommitters")}
@appliesTo{generic()}
@historic{}
int numberOfActiveCommitters(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});
    
@metric{numberOfActiveCommittersLongTerm}
@doc{Number of active committers over time}
@friendlyName{numberOfActiveCommittersLongTerm}
@uses{("longerTermActiveCommitters" :"activeCommitters")}
@appliesTo{generic()}
int numberOfActiveCommittersLongTerm(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});

@metric{maximumActiveCommittersEver}
@doc{What is the maximum number of committers who have been active together in any two week period?}
@friendlyName{maximumActiveCommittersEver}
@uses{("numberOfActiveCommitters.historic" :"history")}
@appliesTo{generic()}
int maximumActiveCommittersEver(rel[datetime d, int n] history = {}) {
  return max(history<n>);
}

@metric{developmentTeamStability}
@doc{based on committer activity, what is the health of the community?}
@friendlyName{Development team stability}
@uses{("numberOfActiveCommitters.historic" :"history"
      ,"maximumActiveCommittersEver":"maxDevs"
      ,"numberOfActiveCommitters":"activeDevs"
      ,"numberOfActiveCommittersLongTerm":"longTermActive")}
@appliesTo{generic()}
Factoid developmentTeamStability(rel[datetime day, int active] history = {}, int maxDevs = 0, int activeDevs = 0, int longTermActive = 0) {
  sorted = sort(history, bool(tuple[datetime,int] a, tuple[datetime,int] b) { return a[0] < b[0]; });
  
  halfYearAgo = decrementMonths(sorted[-1].day, 6);  
  lastYear = [<d,m> | <d,m> <- sorted, d > halfYearAgo];
  sl = size(lastYear) > 2 ? slope([<i,lastYear[i][1]> | i <- index(lastYear)]) : 0;

  stability = \one();
  team = "";
  
  if (-0.1 < sl && sl < 0.1 && longTermActive > 0) {
    stability = \three(); 
    team = "In the last half year the development team was stable and active.";
  }
  else if (-0.1 < sl && sl < 0.1 && longTermActive == 0) {
    stability = \one(); 
    team = "The project has seen hardly anybody developing in the last half year.";
  } 
  else if (sl < 0 && longTermActive > 0) {
    stability = \two(); 
    team = "People have been leaving the development team in the last half year.";
  }
  else if (sl > 0) {
    stability = \four(); 
    team = "In the last half year the development team has been growing.";
  }
  
  txt = "<team>
        'The maximum number of active developers for this project during its lifetime is <maxDevs>,
        'and in the last two weeks there were <activeDevs> people actively developing, as compared to 
        '<longTermActive> in the last six months.";
        
  return factoid(txt, stability);
}

