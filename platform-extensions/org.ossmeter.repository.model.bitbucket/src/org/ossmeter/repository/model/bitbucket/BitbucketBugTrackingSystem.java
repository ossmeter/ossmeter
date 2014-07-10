package org.ossmeter.repository.model.bitbucket;

import com.mongodb.*;

import java.util.*;

import com.googlecode.pongo.runtime.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class BitbucketBugTrackingSystem extends
        org.ossmeter.repository.model.BugTrackingSystem {

    // protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "bitbucket";
    }

    @Override
    public String getInstanceId() {
        return "TODO";
    }

    // protected region custom-fields-and-methods end

    public BitbucketBugTrackingSystem() {
        super();
    }

}