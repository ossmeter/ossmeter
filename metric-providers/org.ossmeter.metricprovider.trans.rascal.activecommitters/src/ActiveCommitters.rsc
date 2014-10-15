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

@metric{firstLastCommitDatesPerDeveloper}
@doc{firstLastCommitDatesPerDeveloper}
@friendlyName{First and last commit dates per developer}
@uses = ("committersToday":"committersToday")
@appliesTo{generic()}
map[str, tuple[datetime, datetime]] firstLastCommitDates(ProjectDelta delta = \empty(), map[str, tuple[datetime first, datetime last]] prev = (), 
  set[str] committersToday = {}) {
  map[str, tuple[datetime, datetime]] developerCommitDates = ();
  for (author <- committersToday) {
    if (author in prev) {
      // don't assume the dates in delta are newer than in prev.
      // for instance, then can get mixed up during conversion from svn to git
      developerCommitDates[author] = <min([delta.date, prev[author].first]), max([delta.date, prev[author].last])>;
    } else {
      developerCommitDates[author] = <delta.date, delta.date>;
    }
  }
  return developerCommitDates;
}

@metric{committersAge}
@doc{Age of committers}
@friendlyName{Age of committers}
@uses = ("firstLastCommitDatesPerDeveloper" : "commitDates")
@appliesTo{generic()}
rel[str, int] ageOfCommitters(map[str, tuple[datetime first, datetime last]] commitDates = ()) {
  return { <author, daysDiff(commitDates[author].first, commitDates[author].last)> | author <- commitDates };
}

@metric{developmentTeam}
@doc{Development team}
@friendlyName{Development team}
@uses = ("committersToday" : "committersToday")
@appliesTo{generic()}
set[str] developmentTeam(set[str] prev = {}, set[str] committersToday = {}) {
  return prev + committersToday;
}

@metric{sizeOfDevelopmentTeam}
@doc{Size of development team}
@friendlyName{Size of development team}
@uses = ("developmentTeam" : "team")
@appliesTo{generic()}
int sizeOfDevelopmentTeam(set[str] team = {}) {
  return size(team);
}

@metric{activeCommitters}
@doc{Committers who have been active the last two weeks}
@friendlyName{committersLastTwoWeeks}
@uses = ("committersToday":"committersToday")
@appliesTo{generic()}
rel[datetime, set[str]] activeCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, committersToday>};  
}

@metric{longerTermActiveCommitters}
@doc{Committers who have been active the last 12 months}
@friendlyName{committersLastYear}
@uses = ("committersToday":"committersToday")
@appliesTo{generic()}
rel[datetime, set[str]] longerTermActiveCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  twelvemonths = decrementMonths(today, 12);
  return {<d,t> | <d,t> <- prev, d > twelvemonths} + {<today, committersToday>};  
}


@metric{numberOfActiveCommitters}
@doc{Number of active committers over time}
@friendlyName{numberOfActiveCommitters}
@uses = ("activeCommitters" :"activeCommitters")
@appliesTo{generic()}
@historic{}
int numberOfActiveCommitters(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});
    
@metric{numberOfActiveCommittersLongTerm}
@doc{Number of long time active committers over time}
@friendlyName{numberOfActiveCommittersLongTerm}
@uses = ("longerTermActiveCommitters" :"activeCommitters")
@appliesTo{generic()}
@historic
int numberOfActiveCommittersLongTerm(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});

@metric{maximumActiveCommittersEver}
@doc{What is the maximum number of committers who have been active together in any two week period?}
@friendlyName{maximumActiveCommittersEver}
@uses = ("numberOfActiveCommitters.historic" :"history")
@appliesTo{generic()}
int maximumActiveCommittersEver(rel[datetime d, int n] history = {}) {
  if (size(history) > 0) {
    return max(history<n>);
  }
  return 0;
}

@metric{developmentTeamStability}
@doc{based on committer activity, what is the health of the community?}
@friendlyName{Development team stability}
@uses = ("numberOfActiveCommitters.historic" :"history"
      ,"maximumActiveCommittersEver":"maxDevs"
      ,"numberOfActiveCommitters":"activeDevs"
      ,"sizeOfDevelopmentTeam":"totalDevs"
      ,"numberOfActiveCommittersLongTerm":"longTermActive")
@appliesTo{generic()}
Factoid developmentTeamStability(rel[datetime day, int active] history = {}, int maxDevs = 0, int totalDevs = 0, int activeDevs = 0, int longTermActive = 0) {
  sl = historicalSlope(history, 6);

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
        'The total number of developers who have worked on this project ever is <totalDevs>.
        'The maximum number of active developers for this project during its lifetime is <maxDevs>,
        'and in the last two weeks there were <activeDevs> people actively developing, as compared to 
        '<longTermActive> in the last twelve months.";
        
  return factoid(txt, stability);
}

@metric{projectAge}
@doc{Age of the project (nr of days between first and last commit)}
@friendlyName{Age of the project (nr of days between first and last commit)}
@uses = ("firstLastCommitDatesPerDeveloper":"firstLastCommitDatesPerDeveloper")
@appliesTo{generic()}
int projectAge(map[str, tuple[datetime first, datetime last]] firstLastCommitDates = ()) {
  if (firstLastCommitDates == ()) {
    throw undefined("No commit dates available", |tmp:///|);
  }

  firstDate = min([ firstLastCommitDates[name][0] | name <- firstLastCommitDates]);
  lastDate = max([ firstLastCommitDates[name][1] | name <- firstLastCommitDates]);
  
  return daysDiff(lastDate, firstDate) + 1;
}
