module org::ossmeter::metricprovider::Factoid

data StarRating 
  = \one() 
  | \two()
  | \three()
  | \four()
  ;
  
data Factoid
  = factoid(str freetext, StarRating rating);