package be.vbgn.gradle.cidetect.impl.travis;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.Map;
import javax.annotation.Nullable;

/***
 * https://docs.travis-ci.com/user/environment-variables/#default-environment-variables
 */
class TravisCiInformation implements CiInformation {

    private final Map<String, String> env;

    TravisCiInformation(Map<String, String> env) {
        this.env = env;
    }

    @Override
    public boolean isCi() {
        return env.containsKey("TRAVIS");
    }

    @Nullable
    @Override
    public String getBuildNumber() {
        return env.getOrDefault("TRAVIS_BUILD_NUMBER", null);
    }

    @Nullable
    @Override
    public String getBranch() {
        return isPullRequest() ? env.getOrDefault("TRAVIS_PULL_REQUEST_BRANCH", null)
                : env.getOrDefault("TRAVIS_BRANCH", null);
    }

    @Override
    public boolean isPullRequest() {
        return !"false".equals(env.getOrDefault("TRAVIS_PULL_REQUEST", "false"));
    }

    @Nullable
    @Override
    public String getPullRequest() {
        return isPullRequest() ? env.getOrDefault("TRAVIS_PULL_REQUEST", null) : null;
    }

    @Nullable
    @Override
    public String getPullRequestTargetBranch() {
        return isPullRequest() ? env.getOrDefault("TRAVIS_BRANCH", null) : null;
    }

    @Override
    public boolean isTag() {
        return !env.getOrDefault("TRAVIS_TAG", "").isEmpty();
    }

    @Nullable
    @Override
    public String getTag() {
        return isTag() ? env.getOrDefault("TRAVIS_TAG", null) : null;
    }
}
