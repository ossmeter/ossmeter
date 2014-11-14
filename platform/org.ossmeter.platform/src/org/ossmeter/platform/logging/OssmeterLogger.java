package org.ossmeter.platform.logging;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.log4mongo.MongoDbAppender;

public class OssmeterLogger extends Logger {

	private static OssmeterLoggerFactory ossmeterFactory = new OssmeterLoggerFactory();
	public static final String DEFAULT_PATTERN = "%-5p [%c] (%d{HH:mm:ss}): %m%n";
	public static final String MONGODB_APPENDER_NAME = "MongoDB";
	
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
	
	public void addRollingAppender (String filename, String pattern) throws IOException{
		this.addAppender(new DailyRollingFileAppender(new PatternLayout(pattern), filename, "'.'yyyy-MM-dd"));
	}
	
	public void addMongoDBAppender(Properties prop) {
//		PropertyConfigurator.configure(prop);
//		MongoDbAppender app = new MongoDbAppender();
//		app.setDatabaseName("logging");
//		app.setCollectionName("log");
//		this.addAppender(app);
	}
}
