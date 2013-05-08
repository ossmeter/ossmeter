package org.ossmeter.platform.logging;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class OssmeterLogger extends Logger {

	private static OssmeterLoggerFactory ossmeterFactory = new OssmeterLoggerFactory();
	public static final String DEFAULT_PATTERN = "%-5p [%c] (%d{HH:mm:ss}): %m%n";
	
	protected OssmeterLogger(String name) {
		super(name);
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name, ossmeterFactory);
	}
	
	// Utility methods to ease usage of the logger:
	
	public void addConsoleAppender(String pattern) {
		this.addAppender(new ConsoleAppender(new PatternLayout(pattern), ConsoleAppender.SYSTEM_OUT));
	}
	
	public void addFileAppender(String filename, String pattern) throws IOException {
		this.addAppender(new FileAppender(new PatternLayout(pattern), filename));
	}
}
