package be.vbgn.gradle.cidetect.impl.github;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * https://help.github.com/en/articles/virtual-environments-for-github-actions#environment-variables
 */
class GithubActionsInformation implements CiInformation {

    private final Map<String, String> env;

    GithubActionsInformation(Map<String, String> env) {
        this.env = env;
    }

    @Override
    public boolean isCi() {
        return env.containsKey("GITHUB_ACTIONS");
    }

    @Nullable
    @Override
    public String getBuildNumber() {
        return env.getOrDefault("GITHUB_SHA", null);
    }

    private String getGithubRefIfStartsWith(String startsWith) {
        String githubRef = env.getOrDefault("GITHUB_REF", null);
        if (githubRef == null) {
            return null;
        }
        if (githubRef.startsWith(startsWith)) {
            return githubRef.substring(startsWith.length());
        }
        return null;
    }

    @Nullable
    @Override
    public String getBranch() {
        if (isPullRequest()) {
            return env.getOrDefault("GITHUB_HEAD_REF", null);
        }
        return getGithubRefIfStartsWith("refs/heads/");
    }

    @Nullable
    @Override
    public String getPullRequest() {
        String pullRef = getGithubRefIfStartsWith("refs/pull/");
        if (pullRef == null) {
            return null;
        }
        if (pullRef.endsWith("/merge")) {
            return pullRef.split("/")[0];
        }
        return null;
    }

    @Nullable
    @Override
    public String getPullRequestTargetBranch() {
        if(isPullRequest()) {
            return env.getOrDefault("GITHUB_BASE_REF", null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getTag() {
        return getGithubRefIfStartsWith("refs/tags/");
    }
}
