package org.ossmeter.platform.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class OssmeterLoggerFactory implements LoggerFactory {

	public OssmeterLoggerFactory() {
		
	}
	
	@Override
	public Logger makeNewLoggerInstance(String loggerName) {
		return new OssmeterLogger(loggerName);
	}

}
