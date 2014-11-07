module Churn

import org::ossmeter::metricprovider::ProjectDelta;
import org::ossmeter::metricprovider::MetricProvider;

import Relation;
import List;
import DateTime;
import analysis::statistics::SimpleRegression;
import analysis::statistics::Descriptive;
import analysis::statistics::Frequency;
import analysis::statistics::Inference;


@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
@appliesTo{generic()}
@historic{}
map[loc, int] churnPerCommit(ProjectDelta delta = \empty()) {
   map[loc, int] result = ();
   for (/VcsRepositoryDelta vcsDelta := delta) {
    result += (vcsDelta.repository.url + co.revision : churn(co) | /VcsCommit co := vcsDelta);
   }
   return result;
}

@metric{commitsToday}
@doc{Counts the number of commits today}
@friendlyName{Number of commits today}
@appliesTo{generic()}
@historic{}
int commitsToday(ProjectDelta delta = \empty()) 
  = (0 | it + 1 |/VcsCommit _ := delta)
  ;
  
@metric{churnToday}
@doc{Counts the churn for today}
@friendlyName{Churn of today}
@appliesTo{generic()}
@historic{}
int commitsToday(ProjectDelta delta = \empty()) 
  = churn(delta);
  

@metric{commitActivity}
@doc{Number of commits in the last two weeks}
@friendlyName{committersLastTwoWeeks}
@uses = ("commitsToday":"commitsToday")
@appliesTo{generic()}
rel[datetime, int] commitActivity(ProjectDelta delta = \empty(), rel[datetime,int] prev = {}, int commitsToday = 0) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, commitsToday>};  
}

@metric{churnActivity}
@doc{Churn over the last two weeks}
@friendlyName{Churn in the last two weeks}
@uses = ("churnToday":"churnToday")
@appliesTo{generic()}
rel[datetime, int] churnActivity(ProjectDelta delta = \empty(), rel[datetime,int] prev = {}, int churnToday = 0) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, churnToday>};  
}

@metric{churnInTwoWeeks}
@doc{Sum of churn over the last two weeks}
@friendlyName{Sum of churn in the last two weeks}
@uses = ("churnActivity":"activity")
@appliesTo{generic()}
@historic{}
int churnInTwoWeeks(rel[datetime, int] activity = {}) 
  = (0 | it + ch | <_, ch> <- activity);
  
@metric{commitsInTwoWeeks}
@doc{Number of commits in the last two weeks}
@friendlyName{Number of commits in the last two weeks}
@uses = ("commitActivity":"activity")
@appliesTo{generic()}
@historic{}
int commitsInTwoWeeks(rel[datetime, int] activity = {}) 
  = (0 | it + ch | <_, ch> <- activity);
  
@metric{churnPerCommitInTwoWeeks}
@doc{The ration between the churn and the number of commits indicates how each commits is on average}
@friendlyName{churnPerCommitInTwoWeeks}
@uses = ("commitsInTwoWeeks":"commits","churnInTwoWeeks":"churn")
@appliesTo{generic()}
@historic{}
int churnPerCommitInTwoWeeks(int churn = 0, int commits = 1) = churn / commits;  

@metric{commitSize}
@doc{Commit frequency and size}
@friendlyName{Commit frequency and size}
@appliesTo{generic()}
@uses= ("churnPerCommitInTwoWeeks.historic":"ratioHistory",
        "churnPerCommitInTwoWeeks":"ratio")
Factoid commitSize(rel[datetime, int] ratioHistory = {}, int ratio = 0) {
  ratioSlope = historicalSlope(ratioHistory, 6);
  ratioMedian = historicalMedian(ratioHistory, 6);

   msg = "In the last two weeks the average size of a commit was <ratio>, this is <if (ratio > ratioMedian) {>more<}else{>less<}>
         'than the normal <ratioMedian> in the last six months. The trend is <slopeText(ratioSlope, "going down", "stable", "going up")>.";
  
  // TODO: the magic constants are not well tested here
  if (ratio < 500) {
    if (ratioMedian > 250) {
      return factoid(msg, \two());
    }
    else if (ratioMedian > 125) {
      return factoid(msg, \three());
    }
    else {
      return factoid(msg, \four());
    }    
  }
  else {
    return factoid(msg, \one());
  }       
}

@metric{churnVolume}
@doc{Churn Volume}
@friendlyName{Churn Volume}
@appliesTo{generic()}
@uses= ("churnInTwoWeeks.historic":"churnHistory", "churnInTwoWeeks":"churn")
Factoid churnVolume(rel[datetime, int] churnHistory = {}, int churn = 0) {
  churnSlope = historicalSlope(churnHistory, 6); 
  churnMedian = historicalMedian(churnHistory, 6);
  
  msg = "In the last two weeks the churn was <churn>, this is <if (churn > churnMedian) {>more<}else{>less<}>
        'than the normal <churnMedian> in the last six months. The trend is <slopeText(churnSlope, "going down", "stable", "going up")>.";
  
  if (churn > 0) {
    if (churnMedian < 100) {
      return factoid(msg, \two());
    }
    else if (churnMedian < 500) {
      return factoid(msg, \three());
    }
    else {
      return factoid(msg, \four());
    }    
  }
  else {
    return factoid(msg, \one());
  }       
}

@metric{churnPerCommitter}
@doc{Count churn per committer}
@friendlyName{Counts number of lines added and deleted per committer over the lifetime of the project}
@appliesTo{generic()}
@historic{}
map[loc author, int churn] churnPerCommitter(ProjectDelta delta = \empty())
  = sumPerItem([<|author:///| + co.author, churn(co)> | /VcsCommit co := delta])
  ;
  
private map[loc, int] sumPerItem(lrel[loc item, int val] input)
  = (x : s | x <- { * input<item> }, int s := filt(input, x));  
 
private int filt(lrel[loc, int] input, loc i) = (0 | it + nu | nu <- [n | <i, n> <- input]);
  
@metric{churnPerFile}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per file over the lifetime of the project}
@appliesTo{generic()}
map[loc file, int churn] churnPerFile(ProjectDelta delta = \empty(), map[loc file, int churn] prev = ()) {
  result = prev;
  for (/VcsRepositoryDelta rd := delta, /VcsCommitItem co := rd) {
    result[rd.repository.url + co.path]?0 += churn(co);
  }
  return result;
}
      
int churn(node item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;

@metric{filesPerCommit}
@doc{Counts the number of files per commit}
@friendlyName{Number of files per commit}
@appliesTo{generic()}
@historic{}
map[loc, int] numberOfFilesPerCommit(ProjectDelta delta = \empty()) {
  map[loc, int] result = ();
  
  for (/VcsRepositoryDelta vcrd <- delta) {
    for (/VcsCommit vc <- delta) {
      result[vcrd.repository.url + vc.revision]? 0 += size(vc.items);
    }
  }
  
  return result;
}

@metric{commitLocality}
@doc{Find out if commits are usually local to a file or widespread over the system}
@friendlyName{Commit locality}
@appliesTo{generic()}
@uses=("filesPerCommit":"filesPerCommit.historic")
Factoid commitLocality(rel[datetime day, map[loc, int] files] filesPerCommit = {}) {
   counts = [ d[f] | <_, map[loc, int] d> <- filesPerCommit, loc f <- d];
   if (counts == []) {
      throw undefined("No commit data available.", |tmp:///|);
   }
   
   med = median(counts);
   
   if (med <= 1) {
     return factoid("Commits are usually local to a single file.", \four());
   }
   else if (med <= 5) {
     return factoid("Commits are usually local to a small group of files.", \three());
   }
   else if (med <= 10) {
     return factoid("Commits usually include between 5 and 10 files.", \two());
   }
   else {
     return factoid("Commits usually include more than 10 files.", \one());
   }  
}
  
@metric{coreCommittersChurn}
@doc{Find the core committers and the churn they have produced}
@friendlyName{Churn per core committer}
@appliesTo{generic()}
@uses = ("churnPerCommitter" : "committerChurn")
map[loc, int] coreCommittersChurn(map[loc, int] prev = (), map[loc, int] committerChurn = ()) 
  = prev + (author : prev[author]?0 + committerChurn[author] | author <- committerChurn);
  
  
