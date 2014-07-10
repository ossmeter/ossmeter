package org.ossmeter.platform.bugtrackingsystem.jira.api;

import java.util.Iterator;

public class JiraIssues implements Iterable<JiraIssue> {

    private final JiraRestClient jira;
    private final String jql;

    public JiraIssues(JiraRestClient jira, String jql) {
        this.jira = jira;
        this.jql = jql;
    }

    @Override
    public Iterator<JiraIssue> iterator() {
        return new JiraIssueIterator();
    }

    private class JiraIssueIterator implements Iterator<JiraIssue> {
        private int total;
        private int retrieved = 0;
        private Iterator<JiraIssue> iterator;

        @Override
        public boolean hasNext() {
            if (null == iterator) {
                return getNextPage();
            } else if (iterator.hasNext()) {
                return true;
            } else if (retrieved >= total) {
                return false;
            } else {
                return getNextPage();
            }
        }

        private boolean getNextPage() {
            JiraSearchResult result;
            try {
                result = jira.search(jql, retrieved, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            total = result.getTotal();
            retrieved += result.getIssues().size();
            iterator = result.getIssues().iterator();
            return iterator.hasNext();
        }

        @Override
        public JiraIssue next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
