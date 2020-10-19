package be.vbgn.gradle.cidetect.impl.appveyor;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * https://www.appveyor.com/docs/environment-variables/
 */
public class AppveyorInformation implements CiInformation {

    private final Map<String, String> env;

    AppveyorInformation(Map<String, String> env) {
        this.env = env;
    }

    @Override
    public boolean isCi() {
        return env.containsKey("APPVEYOR");
    }

    @Nullable
    @Override
    public String getBuildNumber() {
        return env.getOrDefault("APPVEYOR_BUILD_NUMBER", null);
    }

    @Nullable
    @Override
    public String getBranch() {
        if (isPullRequest()) {
            return env.getOrDefault("APPVEYOR_PULL_REQUEST_HEAD_REPO_BRANCH", null);
        }
        return env.getOrDefault("APPVEYOR_REPO_BRANCH", null);
    }

    @Nullable
    @Override
    public String getPullRequest() {
        return env.getOrDefault("APPVEYOR_PULL_REQUEST_NUMBER", null);
    }

    @Nullable
    @Override
    public String getPullRequestTargetBranch() {
        if (isPullRequest()) {
            return env.getOrDefault("APPVEYOR_REPO_BRANCH", null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getTag() {
        return env.getOrDefault("APPVEYOR_REPO_TAG_NAME", null);
    }
}
