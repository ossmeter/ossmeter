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

data MetricException
 = 
 // The metric is undefined for the given input and no sensible default value can be given: 
 undefined(str reason, loc subject)
 ;

  
