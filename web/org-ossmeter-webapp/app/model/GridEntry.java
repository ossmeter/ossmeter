package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class GridEntry extends Pongo {
	
	
	
	public GridEntry() { 
		super();
		COL.setOwningType("model.GridEntry");
		ROW.setOwningType("model.GridEntry");
		SIZEX.setOwningType("model.GridEntry");
		SIZEY.setOwningType("model.GridEntry");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	
	
	public int getCol() {
		return parseInteger(dbObject.get("col")+"", 0);
	}
	
	public GridEntry setCol(int col) {
		dbObject.put("col", col);
		notifyChanged();
		return this;
	}
	public int getRow() {
		return parseInteger(dbObject.get("row")+"", 0);
	}
	
	public GridEntry setRow(int row) {
		dbObject.put("row", row);
		notifyChanged();
		return this;
	}
	public int getSizeX() {
		return parseInteger(dbObject.get("sizeX")+"", 0);
	}
	
	public GridEntry setSizeX(int sizeX) {
		dbObject.put("sizeX", sizeX);
		notifyChanged();
		return this;
	}
	public int getSizeY() {
		return parseInteger(dbObject.get("sizeY")+"", 0);
	}
	
	public GridEntry setSizeY(int sizeY) {
		dbObject.put("sizeY", sizeY);
		notifyChanged();
		return this;
	}
	
	
	
	
}