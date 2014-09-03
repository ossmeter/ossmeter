module ck::NOC

public map[loc, int] NOC(M3 m) {
  map[loc, int] classWiseChildren = ();
  map[loc, set[loc]] inheritanceMap = toMap(m@extends<1,0>);
  
  return (class : size(inheritanceMap[class]) | class <- inheritanceMap);
}