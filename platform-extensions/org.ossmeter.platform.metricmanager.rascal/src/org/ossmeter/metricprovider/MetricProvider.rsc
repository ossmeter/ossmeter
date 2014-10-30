@doc{

This how to add a new metric to OSSMETER. You write a function with a number of tags with meta-information,
and a number of (optional) keyword parameters which provide information from the context. 

@metric{yourMetricId}

@doc{An explanation of the metric}

@friendlyName{A user-interface friendly label for the metric}

@uses = ("idOfMetricYouWishToUse":"nameOfKeywordParameter","idOfMetricYouWishToUse2":"nameOfKeywordParameter2")

// languages you wish the metric to apply on, could be java(), php(), generic():
@appliesTo{generic()} 

// and finally the function definition itself with keyword parameters:
ReturnType anyNameUsuallyTheSameAsMetricId(
  // model of what happened since yesterday:
  ProjectDelta delta = \empty(),      
  
  // latests M3 models for each file in the project, sorted by language:
  rel[Language, loc, M3] m3s = {},   
  
  // latests AST models for each file in the project
  rel[Language, loc, AST] asts = {}   
  
  // the previous value of the metric (for cumulative metrics):
  ReturnType prev = ... /* default */, 
  
  // newest values of other metrics you wish to use:
  TypeOfMetricYouWishToUse nameOfKeywordParameter = .../* default */,
  TypeOfMetricYouWishToUse2 nameOfKeywordParameter2 = ... /* default */
  ) {
  
    .. metric computation ...
    return MetricValue;  
  }
}
module org::ossmeter::metricprovider::MetricProvider

extend org::ossmeter::metricprovider::ProjectDelta;
extend org::ossmeter::metricprovider::Factoid;

import analysis::statistics::SimpleRegression;
import analysis::statistics::Inference;
import analysis::statistics::Frequency;
import analysis::statistics::Descriptive;
import DateTime;
import String;
import util::Math;
import Set;
import List;
import Relation;
import Map;

data MetricException
 = 
 // The metric is undefined for the given input and no sensible default value can be given: 
 undefined(str reason, loc subject)
 ;

  
real historicalSlope(rel[datetime day, num amount] history, int monthsAgo) {
  if (history == {}) {
    throw undefined("No history available for slope computation.", |unknown:///|);
  }

  sorted = sort(history, bool(tuple[datetime, num] a, tuple[datetime, num] b) { return a[0] < b[0]; });
  lastYear = [<d,m> | <d,m> <- sorted, d > decrementMonths(sorted[-1].day, monthsAgo)];
  return size(lastYear) > 2 ? toReal(slope([<i,lastYear[i][1]> | i <- index(lastYear)])) : 0.0;
}

str slopeText(real slope, str down, str stable, str up) {
  if (-0.1 <= slope && slope <= 0.1) {
    return stable;
  }
  else if (slope < 0) {
    return down;
  }
  else {
    return up;
  }
}

num historicalMedian(rel[datetime day, num amount] history, int monthsAgo) {
  if (history == {}) {
    throw undefined("No history available for median computation.", |unknown:///|);
  }
  
  latestDate = max(domain(history));  
  threshold = decrementMonths(latestDate, monthsAgo);
  
  return median([ m | <d,m> <- history, d > threshold]);
}

real spreadOverItems(map[value item, int amount] d) {
  if (sum(d<amount>) == 0) {
    return 1.0; // completely honest distribution of nothing over everything.
  }
  
  dist = distribution(d);
  return gini([<0,0>] + [<x, dist[x]> | x <- dist]);
}

map[str, real] quartiles(map[&T, num] m) {
  if (size(m) < 4) {
    return undefined("Not enough data available", |tmp:///|);
  }

  map[str, real] result = ();
  
  values = sort([ m[i] | i <- m ]);
  
  numValues = size(values);
  
  result["Q1"] = toReal(values[round(numValues * 0.25)]);
  result["Q2"] = toReal(values[round(numValues * 0.5)]);
  result["Q3"] = toReal(values[round(numValues * 0.75)]);
  result["Max"] = toReal(values[-1]);
  
  return result;
}
