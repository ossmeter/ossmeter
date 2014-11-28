package models;

import java.util.List;

public class QualityAttribute extends QualityElement {

    public String description;
    public String detail; // One of: none, src, cc, bts. This is in order to show the "Details" tab.. Needs a better solution in the long term
    public List<String> factoids;
    public List<QualityMetric> metrics;
    
    public QualityAttribute() {
    
    }
}