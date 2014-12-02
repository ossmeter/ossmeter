/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SourceForgeSearchResult {
    private List<Integer> ticketIds = new ArrayList<Integer>();
    private int count;

    public void addTicketId(int ticketId) {
        ticketIds.add(ticketId);
    }

    public List<Integer> getTicketIds() {
        return Collections.unmodifiableList(ticketIds);
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
