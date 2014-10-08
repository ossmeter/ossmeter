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
