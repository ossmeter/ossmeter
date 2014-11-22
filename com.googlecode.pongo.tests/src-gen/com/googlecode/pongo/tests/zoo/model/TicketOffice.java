package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class TicketOffice extends Building {
	
	
	
	public TicketOffice() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Building");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.TicketOffice");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}