package com.palantir.stash.codesearch.hook;

import com.atlassian.stash.pull.PullRequestRef;
import com.atlassian.stash.scm.pull.*;
import com.palantir.stash.codesearch.updater.SearchUpdater;

public class MergeUpdaterHook implements MergeRequestCheck {

    /**
     * We should allocate some time for the merge to occur before indexing. Unfortunately, there's
     * no way AFAIK to make Stash execute this hook right after the merge occurs.
     */
    private static final int MERGE_UPDATE_DELAY = 20000; // milliseconds

    private final SearchUpdater updater;

    public MergeUpdaterHook (SearchUpdater updater) {
        this.updater = updater;
    }

    @Override
    public void check (MergeRequest request) {
        PullRequestRef pr = request.getPullRequest().getToRef();
        updater.submitAsyncUpdate(pr.getRepository(), pr.getId(), MERGE_UPDATE_DELAY);
    }

}