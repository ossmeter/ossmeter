package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class PlotGridEntry extends MetricGridEntry {
	
	
	
	public PlotGridEntry() { 
		super();
		super.setSuperTypes("model.MetricGridEntry","model.GridEntry");
		COL.setOwningType("model.PlotGridEntry");
		ROW.setOwningType("model.PlotGridEntry");
		SIZEX.setOwningType("model.PlotGridEntry");
		SIZEY.setOwningType("model.PlotGridEntry");
		PROJECTID.setOwningType("model.PlotGridEntry");
		METRICID.setOwningType("model.PlotGridEntry");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	public static StringQueryProducer PROJECTID = new StringQueryProducer("projectId"); 
	public static StringQueryProducer METRICID = new StringQueryProducer("metricId"); 
	
	
	
	
	
	
}