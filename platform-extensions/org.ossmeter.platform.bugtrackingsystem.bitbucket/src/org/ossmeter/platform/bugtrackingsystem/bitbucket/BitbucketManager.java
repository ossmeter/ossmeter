package org.ossmeter.platform.bugtrackingsystem.bitbucket;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.bitbucket.BitbucketBugTrackingSystem;

public class BitbucketManager implements IBugTrackingSystemManager<BitbucketBugTrackingSystem>{

    @Override
    public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public BugTrackingSystemDelta getDelta(
            BitbucketBugTrackingSystem bugTrackingSystem, Date date)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getFirstDate(BitbucketBugTrackingSystem bugTrackingSystem)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContents(BitbucketBugTrackingSystem bugTrackingSystem,
            BugTrackingSystemBug bug) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContents(BitbucketBugTrackingSystem bugTrackingSystem,
            BugTrackingSystemComment comment) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
