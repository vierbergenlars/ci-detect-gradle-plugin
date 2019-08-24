package be.vbgn.gradle.cidetect.impl.gitlab;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * https://docs.gitlab.com/ee/ci/variables/predefined_variables.html
 * https://docs.gitlab.com/ee/ci/variables/deprecated_variables.html#gitlab-90-renamed-variables
 */
class GitlabCiInformation implements CiInformation {

    private final Map<String, String> env;

    GitlabCiInformation(Map<String, String> env) {
        this.env = env;
    }

    @Override
    public boolean isCi() {
        return env.containsKey("GITLAB_CI");
    }

    @Nullable
    @Override
    public String getBuildNumber() {
        return env.getOrDefault("CI_PIPELINE_IID", null);
    }

    @Nullable
    @Override
    public String getBranch() {
        if(isTag()) {
            return null;
        }
        if(isPullRequest()) {
            return env.getOrDefault("CI_MERGE_REQUEST_SOURCE_BRANCH_NAME", null);
        }
        return env.getOrDefault("CI_COMMIT_REF_NAME", env.getOrDefault("CI_BUILD_REF_NAME", null));
    }

    @Nullable
    @Override
    public String getPullRequest() {
        return env.getOrDefault("CI_MERGE_REQUEST_IID", null);
    }

    @Nullable
    @Override
    public String getPullRequestTargetBranch() {
        return isPullRequest() ? env.getOrDefault("CI_MERGE_REQUEST_TARGET_BRANCH_NAME", null) : null;
    }

    @Nullable
    @Override
    public String getTag() {
        return env.getOrDefault("CI_COMMIT_TAG", env.getOrDefault("CI_BUILD_TAG", null));
    }
}
