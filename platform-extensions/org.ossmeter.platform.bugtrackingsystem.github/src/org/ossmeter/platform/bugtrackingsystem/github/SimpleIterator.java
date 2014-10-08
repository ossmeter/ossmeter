package org.ossmeter.platform.bugtrackingsystem.github;

import java.util.Iterator;

import org.eclipse.egit.github.core.client.PageIterator;

public class SimpleIterator<T> implements Iterator<T> {
	
	private PageIterator<T> pageIterator;
	private Iterator<T> itemIterator;
	
	public SimpleIterator(PageIterator<T> pageIterator) {
		this.pageIterator = pageIterator;
	}

	@Override
	public boolean hasNext() {
		if ( itemIterator != null && itemIterator.hasNext() ) {
			return itemIterator.hasNext();
		} else if ( pageIterator.hasNext() ) {
			itemIterator =  pageIterator.next().iterator();
			return itemIterator.hasNext();
		}
		
		return false;
	}

	@Override
	public T next() {
		return itemIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

}
