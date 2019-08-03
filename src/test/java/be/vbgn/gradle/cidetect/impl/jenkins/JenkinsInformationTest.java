package be.vbgn.gradle.cidetect.impl.jenkins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class JenkinsInformationTest {

    private static final Map<String, String> BASE_ENV = new HashMap<>();

    static {
        BASE_ENV.put("JENKINS_URL", "blabla");
    }

    @Test
    public void testJenkinsBranchBuildInformation() {
        Map<String, String> environment = new HashMap<>(BASE_ENV);
        environment.put("BRANCH_NAME", "master");
        environment.put("BUILD_NUMBER", "12");

        CiInformation ciInformation = new JenkinsInformation(environment);

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
    public void testJenkinsPullRequestBuildInformation() {
        Map<String, String> environment = new HashMap<>(BASE_ENV);
        environment.put("BRANCH_NAME", "fix-issue");
        environment.put("BUILD_NUMBER", "12");
        environment.put("CHANGE_ID", "5");
        environment.put("CHANGE_TARGET", "master");

        CiInformation ciInformation = new JenkinsInformation(environment);

        assertTrue(ciInformation.isCi());
        assertEquals("fix-issue", ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertTrue(ciInformation.isPullRequest());
        assertEquals("5", ciInformation.getPullRequest());
        assertEquals("master", ciInformation.getPullRequestTargetBranch());
        assertFalse(ciInformation.isTag());
        assertNull(ciInformation.getTag());
    }

}
