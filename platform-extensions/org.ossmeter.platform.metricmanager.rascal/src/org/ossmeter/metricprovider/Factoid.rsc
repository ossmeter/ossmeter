module org::ossmeter::metricprovider::Factoid

data StarRating 
  = \one() 
  | \two()
  | \three()
  | \four()
  ;
  
public map[int, StarRating] starLookup = (1:\one(), 2:two(), 3:three(), 4:four());
  
data Factoid
  = factoid(str freetext, StarRating rating);