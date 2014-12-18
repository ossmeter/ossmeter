package org.ossmeter.platform.communicationchannel.zendesk;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.ossmeter.platform.communicationchannel.zendesk.model.Ticket;

public class ZendapiTest {

	@Test
	public void test() {
		org.ossmeter.platform.communicationchannel.zendesk.Zendesk z;
		z = new org.ossmeter.platform.communicationchannel.zendesk.Zendesk.Builder("https://univaq.zendesk.com")
			.setUsername("juri.dirocco@univaq.it").setPassword("Pavone84").build();
		List<Ticket> t = z.getTickets(1,10);
		for (Ticket ticket : t) {
			System.out.println(ticket.toString());
		}
	}

}
