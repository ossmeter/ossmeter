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
import DateTime;
import String;
import util::Math;
import Set;
import List;

data MetricException
 = 
 // The metric is undefined for the given input and no sensible default value can be given: 
 undefined(str reason, loc subject)
 ;

  
real historicalSlope(rel[datetime day, num amount] history, int monthsAgo) {
  if (history == {}) {
    throw undefined("No history available for slope computation.", |unknown:///|);
  }
    
  sorted = sort(history, bool(tuple[datetime,int] a, tuple[datetime,int] b) { return a[0] < b[0]; });
  lastYear = [<d,m> | <d,m> <- sorted, d > decrementMonths(sorted[-1].day, monthsAgo)];
  return size(lastYear) > 2 ? toReal(slope([<i,lastYear[i][1]> | i <- index(lastYear)])) : 0.0;
}

real spreadOverItems(map[value item, int amount] d) {
  if (sum(d<amount>) == 0) {
    return 1.0; // completely honest distribution of nothing over everything.
  }
  
  dist = distribution(d);
  return gini([<0,0>] + [<x, dist[x]> | x <- dist]);
}
  
