package be.vbgn.gradle.cidetect.impl.travis;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class TravisCiInformationTest {

    private static final Map<String, String> BASE_ENV = new HashMap<>();

    static {
        BASE_ENV.put("CI", "true");
        BASE_ENV.put("TRAVIS", "true");
        BASE_ENV.put("CONTINUOUS_INTEGRATION", "true");
        BASE_ENV.put("USER", "travis");
        BASE_ENV.put("HOME", "/home/travis");
    }


    @Test
    public void testTravisCiBranchBuildInformation() {
        Map<String, String> environment = new HashMap<>(BASE_ENV);
        environment.put("TRAVIS_BRANCH", "master");
        environment.put("TRAVIS_PULL_REQUEST", "false");
        environment.put("TRAVIS_BUILD_NUMBER", "12");
        environment.put("TRAVIS_PULL_REQUEST_BRANCH", "");
        environment.put("TRAVIS_SECURE_ENV_VARS", "true");
        environment.put("TRAVIS_TAG", "");
        environment.put("TRAVIS_EVENT_TYPE", "push");

        CiInformation ciInformation = new TravisCiInformation(environment);

        assertTrue(ciInformation.isCi());
        assertEquals("master", ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertFalse(ciInformation.isPullRequest());
        assertNull(ciInformation.getPullRequest());
        assertNull(ciInformation.getPullRequestTargetBranch());
        assertFalse(ciInformation.isTag());
        assertNull(ciInformation.getTag());
    }

    @Test
    public void testTravisCiPullRequestBuildInformation() {
        Map<String, String> environment = new HashMap<>(BASE_ENV);
        environment.put("TRAVIS_BRANCH", "master");
        environment.put("TRAVIS_PULL_REQUEST", "5");
        environment.put("TRAVIS_BUILD_NUMBER", "12");
        environment.put("TRAVIS_PULL_REQUEST_BRANCH", "fix-issue");
        environment.put("TRAVIS_SECURE_ENV_VARS", "false");
        environment.put("TRAVIS_TAG", "");
        environment.put("TRAVIS_EVENT_TYPE", "pull_request");

        CiInformation ciInformation = new TravisCiInformation(environment);

        assertTrue(ciInformation.isCi());
        assertEquals("fix-issue", ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertTrue(ciInformation.isPullRequest());
        assertEquals("5", ciInformation.getPullRequest());
        assertEquals("master", ciInformation.getPullRequestTargetBranch());
        assertFalse(ciInformation.isTag());
        assertNull(ciInformation.getTag());
    }

    @Test
    public void testTravisCiTagBuildInformation() {
        Map<String, String> environment = new HashMap<>(BASE_ENV);
        environment.put("TRAVIS_BRANCH", "v1.2.3");
        environment.put("TRAVIS_PULL_REQUEST", "false");
        environment.put("TRAVIS_BUILD_NUMBER", "12");
        environment.put("TRAVIS_PULL_REQUEST_BRANCH", "");
        environment.put("TRAVIS_SECURE_ENV_VARS", "true");
        environment.put("TRAVIS_TAG", "v1.2.3");
        environment.put("TRAVIS_EVENT_TYPE", "push");

        CiInformation ciInformation = new TravisCiInformation(environment);

        assertTrue(ciInformation.isCi());
        assertEquals("v1.2.3", ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertFalse(ciInformation.isPullRequest());
        assertNull(ciInformation.getPullRequest());
        assertNull(ciInformation.getPullRequestTargetBranch());
        assertTrue(ciInformation.isTag());
        assertEquals("v1.2.3", ciInformation.getTag());
    }

}
