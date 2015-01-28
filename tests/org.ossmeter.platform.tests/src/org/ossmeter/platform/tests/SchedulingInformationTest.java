package org.ossmeter.platform.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.ProjectCollection;
import org.ossmeter.repository.model.SchedulingInformation;
import org.ossmeter.repository.model.SchedulingInformationCollection;

import com.mongodb.Mongo;

public class SchedulingInformationTest {

	@Test
	public void test() throws Exception {
		Mongo mongo = new Mongo();
		Platform platform = new Platform(mongo);
		
		SchedulingInformationCollection col = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation();
		
		String id = "foo";
		
		SchedulingInformation si = new SchedulingInformation();
		si.setWorkerIdentifier(id).setIsMaster(false).getCurrentLoad().add("foo");
		
		col.add(si);
		col.sync();
		
		si = col.findOneByWorkerIdentifier(id);
		
		assertEquals(1, si.getCurrentLoad().size());

		// Attempt to clear it
		si.getCurrentLoad().clear();
		si.setWorkerIdentifier(si.getWorkerIdentifier()); // FIXME: We have to force dirtying
		col.sync();
		
		si = col.findOneByWorkerIdentifier(id);
		assertEquals(0, si.getCurrentLoad().size());
		
		// Clean up
		si = col.findOneByWorkerIdentifier(id);
		col.remove(si);
		col.sync();
		
		mongo.close();
	}

}
