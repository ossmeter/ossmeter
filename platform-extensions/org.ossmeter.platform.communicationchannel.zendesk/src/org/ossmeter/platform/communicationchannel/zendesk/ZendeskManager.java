/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Juri Di Rocco - Implementation.
 *    Davide Di Ruscio - Implementation
 *******************************************************************************/
package org.ossmeter.platform.communicationchannel.zendesk;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.communicationchannel.zendesk.model.Ticket;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.cc.zendesk.Zendesk;

import com.mongodb.DB;

public class ZendeskManager implements ICommunicationChannelManager<Zendesk>{

	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {
		return communicationChannel instanceof Zendesk;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db,
			Zendesk communicationChannel, Date date) throws Exception {
		// TODO Auto-generated method stub
		
		org.ossmeter.platform.communicationchannel.zendesk.Zendesk z;
		z = new org.ossmeter.platform.communicationchannel.zendesk.Zendesk.Builder("")
			.setUsername("").setToken("").build();
		Ticket t = z.getTicket(1);
		return null;
	}

	@Override
	public String getContents(DB db, Zendesk communicationChannel,
			CommunicationChannelArticle article) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getFirstDate(DB db, Zendesk communicationChannel)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
