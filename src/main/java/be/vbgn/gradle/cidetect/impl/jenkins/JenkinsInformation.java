package be.vbgn.gradle.cidetect.impl.jenkins;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * https://github.com/jenkinsci/branch-api-plugin/blob/c4d394415cf25b6890855a08360119313f1330d2/src/main/java/jenkins/branch/BranchNameContributor.java#L63-L92
 */
class JenkinsInformation implements CiInformation {

    private final Map<String, String> env;

    JenkinsInformation(Map<String, String> env) {
        this.env = env;
    }

    /**
     * https://github.com/jenkinsci/jenkins/blob/f0c5108184a75e589493afe6d54879b5c4b7ed54/core/src/main/java/jenkins/model/CoreEnvironmentContributor.java#L53
     */
    @Override
    public boolean isCi() {
        return env.containsKey("JENKINS_HOME");
    }

    /**
     * https://github.com/jenkinsci/jenkins/blob/701d00c9ed7dec35147cee1ea3ac50fe57b5b721/core/src/main/java/hudson/model/Run.java#L2367
     */
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

    @Nullable
    @Override
    public String getTag() {
        return env.getOrDefault("TAG_NAME", null);
    }
}
