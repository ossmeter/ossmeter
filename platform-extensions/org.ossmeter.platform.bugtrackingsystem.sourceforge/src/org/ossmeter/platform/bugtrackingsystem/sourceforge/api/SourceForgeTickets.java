package org.ossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.util.Iterator;

public class SourceForgeTickets implements Iterable<SourceForgeTicket> {

    private final SourceForgeTrackerRestClient sourceforge;
    private final Iterator<Integer> ticketIds;

    public SourceForgeTickets(SourceForgeTrackerRestClient sourceforge,
            Iterator<Integer> ticketIds) {
        this.sourceforge = sourceforge;
        this.ticketIds = ticketIds;
    }

    @Override
    public Iterator<SourceForgeTicket> iterator() {
        return new SourceForgeTicketIterator();
    }

    private class SourceForgeTicketIterator implements
            Iterator<SourceForgeTicket> {
        @Override
        public boolean hasNext() {
            return ticketIds.hasNext();
        }

        @Override
        public SourceForgeTicket next() {
            try {
                return sourceforge.getTicket(ticketIds.next());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
