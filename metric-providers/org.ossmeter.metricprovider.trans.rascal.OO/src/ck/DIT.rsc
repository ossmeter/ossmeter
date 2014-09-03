module ck::DIT

public map[loc, int] DIT(M3 m) {
  map[loc, int] classWiseDIT = ();
  rel[loc, loc] inheritances = m@extends<1,0>;
    
  return (class : getDepth(class, inheritances) | class <- carrier(inheritances));
}

int getDepth(loc curClass, rel[loc, loc] inheritances) {
  set[loc] parents = predecessors(inheritances, curClass);
  if (isEmpty(parents))
    return 0;
  return 1 + max({getDepth(parent, inheritances) | loc parent <- parents});
}