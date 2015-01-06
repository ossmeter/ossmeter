package org.ossmeter.platform.communicationchannel.zendesk.test;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.communicationchannel.zendesk.ZendeskManager;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.cc.zendesk.Zendesk;

import com.mongodb.DB;
import com.mongodb.Mongo;



public class ZendapiTest {

	static private ZendeskManager zendesk = new ZendeskManager();
	
	static private Zendesk communicationChannel = new Zendesk();
	static {
		communicationChannel.setUsername("juri.dirocco@univaq.it");
		communicationChannel.setPassword("Pavone84");
		communicationChannel.setUrl("https://univaq.zendesk.com");
	}
	static Mongo mongo;
	static Platform platform;
	private OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicatonchannel.zendesk");
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	@Rule public ExpectedException expected = ExpectedException.none();
	@Test
	public void testZendeskGetFirstDateMethod() {
		try {
			org.ossmeter.platform.Date d = zendesk.getFirstDate(mongo.getDB("ossmeter"), communicationChannel);
			logger.info("First Date: " + d);
		} catch (Exception e) {
			fail("Thows Exception" + e.getMessage());
		}
		
	}
	
	@Test
	public void testZendeskGetDeltaMethod() {
		try {
			CommunicationChannelDelta d = zendesk.getDelta(mongo.getDB("ossmeter"), communicationChannel, new Date().addDays(-5));
			logger.info("Get Delta: " + d);
		} catch (Exception e) {
			fail("Thows Exception" + e.getMessage());
		}
		
	}
	
	@Test
	public void testZendeskGetContentMethod() {
		try {
			CommunicationChannelDelta d = zendesk.getDelta(mongo.getDB("ossmeter"), communicationChannel, new Date().addDays(-5));
			String s = zendesk.getContents(mongo.getDB("ossmeter"), communicationChannel, d.getArticles().get(0));
			logger.info("get Content " + s);
		} catch (Exception e) {
			fail("Thows Exception" + e.getMessage());
		}
		
	}

}

