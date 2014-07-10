package org.ossmeter.platform.bugtrackingsystem.redmine;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.redmine.RedmineBugIssueTracker;

public class RedmineManager implements IBugTrackingSystemManager<RedmineBugIssueTracker>{

    @Override
    public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public BugTrackingSystemDelta getDelta(
            RedmineBugIssueTracker bugTrackingSystem, Date date)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getFirstDate(RedmineBugIssueTracker bugTrackingSystem)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContents(RedmineBugIssueTracker bugTrackingSystem,
            BugTrackingSystemBug bug) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContents(RedmineBugIssueTracker bugTrackingSystem,
            BugTrackingSystemComment comment) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
