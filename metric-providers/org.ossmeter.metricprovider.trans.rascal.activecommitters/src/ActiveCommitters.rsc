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
import analysis::statistics::Descriptive;
import analysis::statistics::Frequency;
import analysis::statistics::Inference;
 
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

@metric{commitsPerDeveloper}
@doc{commitsPerDeveloper}
@friendlyName{Number of commits per developer}
@appliesTo{generic()}
map[str, int] commitsPerDeveloper(ProjectDelta delta = \empty(), map[str, int] prev = ()) {
  map[str, int] result = prev;
  
  for (/VcsCommit co := delta) {
    result[co.author]?0 += 1;
  }
  
  return result;
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

@metric{developmentTeamExperience}
@doc{Based on committer activity, how experienced is the current team?}
@friendlyName{Development team experience}
@uses = ("firstLastCommitDatesPerDeveloper": "firstLastCommitDatesPerDeveloper", "commitsPerDeveloper": "commitsPerDeveloper")
@appliesTo{generic()}
Factoid developmentTeamExperience(
  ProjectDelta delta = \empty(),
  map[str, tuple[datetime first, datetime last]] firstLastCommitDates = (),
  map[str, int] commitsPerDeveloper = ())
{
  if (delta == \empty() || commitsPerDeveloper == ()) {
    throw undefined("No delta available", |tmp:///|);
  }
  
  today = delta.date;
  sixMonthsAgo = decrementMonths(today, 6);
  
  committersInLastHalfYear = { name | name <- firstLastCommitDates, firstLastCommitDates[name].last > sixMonthsAgo };
  
  experiencedCommittersInLastHalfYear = { name | name <- committersInLastHalfYear,
    firstLastCommitDates[name].last > decrementMonths(firstLastCommitDates[name].first, 6),
    (commitsPerDeveloper[name]?0) > 24 }; // at least 1 commit per week on average
  
  numExperiencedCommitters = size(experiencedCommittersInLastHalfYear);
  
  stars = numExperiencedCommitters + 1;
  
  if (stars > 4) {
    stars = 4;
  }
  
  txt = "";
  
  if (stars == 1) {
    txt = "There were no experienced committers working for the project in the last 6 months.";
  }
  else if (stars == 2) {
    txt = "The was only one experienced committer working for the project in the last 6 months.";
    txt += " Overall, he/she contributed <commitsPerDeveloper[getOneFrom(experiencedCommittersInLastHalfYear)]> commits.";
  }
  else {
    txt = "The number of experienced committers working for the project in the last 6 months is <numExperiencedCommitters>.";
    txt += " Their average overall number of commits is <mean([commitsPerDeveloper[d] | d <- experiencedCommittersInLastHalfYear])>.";
  }

  if (size(committersInLastHalfYear) == numExperiencedCommitters) {
    txt += " There were no other committers active in the last 6 months."; 
  }
  else {
    txt += " In total, <size(committersInLastHalfYear)> committers have worked on the project in the last six months.";
  }

  return factoid(txt, starLookup[stars]);
}

// TODO: this metric is broken because it does not consider the full history @metric{committersoverfile}
@doc{Calculates the gini coefficient of committeroverfile}
@friendlyName{committersoverfile}
@appliesTo{generic()}
@historic{}
real giniCommittersOverFile(ProjectDelta delta = \empty()) {
  rel[str, str] filesCommitters = {< commitItem.path, vcC.author > | /VcsCommit vcC <- delta, commitItem <- vcC.items};

  committersOverFile = distribution(filesCommitters<1,0>);
  distCommitterOverFile = distribution(committersOverFile);
  
  if (size(distCommitterOverFile) > 0) {
    return gini([<0,0>]+[<x, distCommitterOverFile[x]> | x <- distCommitterOverFile]);
  }

  throw undefined("not enough data to compute committer over file spread");
}

// TODO: this metric is broken because it does not consider the full history @metric{NumberOfCommittersperFile}
@doc{Count the number of committers that have touched a file.}
@friendlyName{Number of Committers per file}
@appliesTo{generic()}
@historic{}
map[loc file, int numberOfCommitters] countCommittersPerFile(ProjectDelta delta = \empty()) {
  commPerFile = committersPerFile(delta);
  return (f : size(commPerFile[f]) | f <- commPerFile);
}

map[loc, set[str]] committersPerFile(ProjectDelta delta) {
  map[loc file, set[str] committers] result = ();
  set[str] emptySet = {};
  for (/VcsRepositoryDelta vcrd <- delta) {
    loc repo = vcrd.repository.url;
    for (/VcsCommit vc <- delta, vc.author != "null") {
      for (VcsCommitItem vci <- vc.items) {
        // Need to check that the committer is not already counted
        result[repo+vci.path]? emptySet += {vc.author};
      }
    }
  }
  
  return result;
}


