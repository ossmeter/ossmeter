package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BitbucketLinks implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> values = new HashMap<String, String>();
	
	public Map<String, String> getValues() {
		return values;
	}

}
