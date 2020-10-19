package be.vbgn.gradle.cidetect;

import javax.annotation.Nullable;

class NullCiInformation implements CiInformation {

    @Override
    public boolean isCi() {
        return false;
    }

    @Nullable
    @Override
    public String getBuildNumber() {
        return null;
    }

    @Override
    public String getBranch() {
        return null;
    }

    @Override
    public String getPullRequest() {
        return null;
    }

    @Override
    public String getPullRequestTargetBranch() {
        return null;
    }

    @Override
    public String getTag() {
        return null;
    }

    @Nullable
    @Override
    public String getPlatform() {
        return null;
    }
}
