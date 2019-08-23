package be.vbgn.gradle.cidetect.impl.travis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.impl.AbstractCiInformationTest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class TravisCiInformationTest extends AbstractCiInformationTest {

    @Override
    protected Map<String, String> getBaseEnv(String buildNumber) {
        Map<String, String> env = new HashMap<>();
        env.put("CI", "true");
        env.put("TRAVIS", "true");
        env.put("CONTINUOUS_INTEGRATION", "true");
        env.put("USER", "travis");
        env.put("HOME", "/home/travis");
        env.put("TRAVIS_BUILD_NUMBER", buildNumber);

        env.put("TRAVIS_PULL_REQUEST", "false");
        env.put("TRAVIS_PULL_REQUEST_BRANCH", "");
        env.put("TRAVIS_SECURE_ENV_VARS", "true");
        env.put("TRAVIS_TAG", "");
        env.put("TRAVIS_EVENT_TYPE", "push");
        return env;
    }

    @Override
    protected Map<String, String> getBranchBuildEnv(String branch) {
        return Collections.singletonMap("TRAVIS_BRANCH", branch);
    }

    @Override
    protected Map<String, String> getPullRequestBuildEnv(String sourceBranch, String targetBranch, String prNumber) {
        Map<String, String> env = new HashMap<>();

        env.put("TRAVIS_BRANCH", targetBranch);
        env.put("TRAVIS_PULL_REQUEST", prNumber);
        env.put("TRAVIS_PULL_REQUEST_BRANCH", sourceBranch);
        env.put("TRAVIS_SECURE_ENV_VARS", "false");
        env.put("TRAVIS_EVENT_TYPE", "pull_request");

        return env;
    }

    @Override
    protected Map<String, String> getTagBuildEnv(String tagName) {
        Map<String, String> env = new HashMap<>();
        env.put("TRAVIS_BRANCH", tagName);
        env.put("TRAVIS_TAG", tagName);
        return env;
    }

    @Override
    protected CiInformation createCiInformation(Map<String, String> env) {
        return new TravisCiInformation(env);
    }
}
