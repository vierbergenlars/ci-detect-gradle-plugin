package be.vbgn.gradle.cidetect.impl.jenkins;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.Map;
import javax.annotation.Nullable;

class JenkinsInformation implements CiInformation {

    private final Map<String, String> env;

    JenkinsInformation(Map<String, String> env) {
        this.env = env;
    }

    @Override
    public boolean isCi() {
        return env.containsKey("JENKINS_URL");
    }

    @Nullable
    @Override
    public String getBuildNumber() {
        return env.getOrDefault("BUILD_NUMBER", null);
    }

    @Nullable
    @Override
    public String getBranch() {
        return env.getOrDefault("BRANCH_NAME", null);
    }

    @Nullable
    @Override
    public String getPullRequest() {
        return env.getOrDefault("CHANGE_ID", null);
    }

    @Nullable
    @Override
    public String getPullRequestTargetBranch() {
        return isPullRequest() ? env.getOrDefault("CHANGE_TARGET", null) : null;
    }

    @Override
    public boolean isTag() {
        return false;
    }

    @Nullable
    @Override
    public String getTag() {
        return null;
    }
}
