package models;

import java.util.List;

public class QualityAspect extends QualityElement {

    public String id;
    public List<QualityAttribute> attributes;
    public List<QualityAspect> aspects;
    
    public QualityAspect() {
    
    }
}