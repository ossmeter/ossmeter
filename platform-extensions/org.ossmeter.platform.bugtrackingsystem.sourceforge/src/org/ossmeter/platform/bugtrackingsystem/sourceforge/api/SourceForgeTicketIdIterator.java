package org.ossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.util.Iterator;

public class SourceForgeTicketIdIterator implements Iterator<Integer> {

	private final SourceForgeTrackerRestClient sourceforge;
	private final String query;

	public SourceForgeTicketIdIterator(
			SourceForgeTrackerRestClient sourceforge, String query) {
		this.sourceforge = sourceforge;
		this.query = query;
	}

	private Iterator<Integer> iterator;
	private int total;
	private int page = 0;

	@Override
	public boolean hasNext() {
		if (null == iterator) {
			return getNextPage();
		} else if (iterator.hasNext()) {
			return true;
		} else if (page * SourceForgeConstants.DEFAULT_PAGE_SIZE >= total) {
			return false;
		} else {
			return getNextPage();
		}
	}

	private boolean getNextPage() {
		SourceForgeSearchResult result;
		try {
			result = sourceforge.search(query, page,
					SourceForgeConstants.DEFAULT_PAGE_SIZE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		total = result.getCount();
		iterator = result.getTicketIds().iterator();
		page++;
		return iterator.hasNext();
	}

	@Override
	public Integer next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
