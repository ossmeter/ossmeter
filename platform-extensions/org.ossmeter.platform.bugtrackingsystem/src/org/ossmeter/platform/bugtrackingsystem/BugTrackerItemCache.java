package org.ossmeter.platform.bugtrackingsystem;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.Interval;
import org.ossmeter.repository.model.BugTrackingSystem;

public class BugTrackerItemCache<T, K> {

    public static abstract class Provider<T, K> {
        public abstract Iterable<T> getItems(Date after, Date before,
                BugTrackingSystem bugTracker) throws Exception;

        public abstract boolean changedOnDate(T item, Date date,
                BugTrackingSystem bugTracker);

        public abstract boolean changedSinceDate(T item, Date date,
                BugTrackingSystem bugTracker);

        public abstract K getKey(T item);

        /**
         * returns true if one of dates matches date.
         * 
         * @param date
         * @param dates
         * @return
         */
        public static boolean findMatchOnDate(Date date, Date... dates) {
            for (Date d : dates) {
                if (DateUtils.isSameDay(date, d)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * returns true if one of dates occurred after date.
         * 
         * @param date
         * @param dates
         * @return
         */
        public static boolean findMatchSinceDate(Date date, Date... dates) {
            for (Date d : dates) {
                if (d.after(date)) {
                    return true;
                }
            }

            return false;
        }

    }

    protected Provider<T, K> provider;

    // TODO Could move to Google Guava Caches, if we want a smarter strategy to
    // conserve memory. For example, maybe purge cache after 24 hours?
    private Date latestDate;
    private Date earliestDate;
    private int minUpdateIntervalMillis = 600000;
    private BugTrackingSystem bugTracker;
    private Map<K, T> cache = new HashMap<K, T>();

    public BugTrackerItemCache(BugTrackingSystem bugTracker,
            Provider<T, K> provider) {
        this.bugTracker = bugTracker;
        this.provider = provider;
    }

    public BugTrackerItemCache(BugTrackingSystem bugTracker,
            Provider<T, K> provider, int minUpdateIntervalSecs) {
        this.bugTracker = bugTracker;
        this.provider = provider;
        this.minUpdateIntervalMillis = minUpdateIntervalSecs * 1000;
    }

    public Iterable<T> getItemsOnDate(Date date) throws Exception {
        update(date);
        return new Items(date, false);
    }

    public Iterable<T> getItemsAfterDate(Date date) throws Exception {
        update(date);
        return new Items(date, true);
    }

    private void update(Date date) throws Exception {
        Date now = Calendar.getInstance().getTime();

        if (null == latestDate) {
            Iterable<T> items = provider.getItems(date, null, bugTracker);
            cacheItems(items);
            earliestDate = date;
            latestDate = now;
        } else if (new Interval(latestDate.getTime(), now.getTime())
                .toDurationMillis() > minUpdateIntervalMillis) {
            Iterable<T> items = provider.getItems(latestDate, null, bugTracker);
            cacheItems(items);
            latestDate = now;
        }

        if (date.before(earliestDate)) {
            Iterable<T> items = provider.getItems(date, earliestDate,
                    bugTracker);
            cacheItems(items);
            earliestDate = date;
        }
    }

    private void cacheItems(Iterable<T> items) {
        for (T item : items) {
            // Will automatically overwrite any existing representation of the
            // item
            cache.put(provider.getKey(item), item);
        }
    }

    private class Items implements Iterable<T> {
        private Date date;
        private boolean itemsAfterDate;

        public Items(Date date, boolean itemsAfterDate) {
            this.date = date;
            this.itemsAfterDate = itemsAfterDate;
        }

        @Override
        public Iterator<T> iterator() {
            return new ItemsIterator(cache.values().iterator());
        }

        private class ItemsIterator implements Iterator<T> {
            private T next;
            private Iterator<T> iterator;

            public ItemsIterator(Iterator<T> iterator) {
                this.iterator = iterator;
            }

            @Override
            public boolean hasNext() {
                if (itemsAfterDate) {
                    while (iterator.hasNext()) {
                        next = iterator.next();
                        if (provider.changedSinceDate(next, date, bugTracker)) {
                            return true;
                        }
                    }
                } else {
                    while (iterator.hasNext()) {
                        next = iterator.next();
                        if (provider.changedOnDate(next, date, bugTracker)) {
                            return true;
                        }
                    }
                }

                return false;
            }

            @Override
            public T next() {
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }

}
