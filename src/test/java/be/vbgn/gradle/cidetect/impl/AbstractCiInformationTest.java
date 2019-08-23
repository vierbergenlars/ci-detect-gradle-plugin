package be.vbgn.gradle.cidetect.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public abstract class AbstractCiInformationTest {

    protected abstract Map<String, String> getBaseEnv(String buildNumber);

    protected abstract Map<String, String> getBranchBuildEnv(String branch);

    protected abstract Map<String, String> getPullRequestBuildEnv(String sourceBranch, String targetBranch,
            String prNumber);

    protected abstract Map<String, String> getTagBuildEnv(String tagName);

    protected abstract CiInformation createCiInformation(Map<String, String> env);

    @Test
    public void testBranchBuildInformation() {
        Map<String, String> environment = new HashMap<>(getBaseEnv("12"));
        environment.putAll(getBranchBuildEnv("master"));

        CiInformation ciInformation = createCiInformation(environment);

        assertTrue(ciInformation.isCi());
        assertEquals("master", ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertFalse(ciInformation.isPullRequest());
        assertNull(ciInformation.getPullRequest());
        assertNull(ciInformation.getPullRequestTargetBranch());
        assertFalse(ciInformation.isTag());
        assertNull(ciInformation.getTag());
        assertEquals("master", ciInformation.getReference());
    }

    @Test
    public void testPullRequestBuildInformation() {
        Map<String, String> environment = new HashMap<>(getBaseEnv("12"));
        environment.putAll(getPullRequestBuildEnv("fix-issue", "master", "5"));

        CiInformation ciInformation = createCiInformation(environment);

        assertTrue(ciInformation.isCi());
        assertEquals("fix-issue", ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertTrue(ciInformation.isPullRequest());
        assertEquals("5", ciInformation.getPullRequest());
        assertEquals("master", ciInformation.getPullRequestTargetBranch());
        assertFalse(ciInformation.isTag());
        assertNull(ciInformation.getTag());
        assertEquals("fix-issue", ciInformation.getReference());
    }

    @Test
    public void testTagBuildInformation() {
        Map<String, String> environment = new HashMap<>(getBaseEnv("12"));
        environment.putAll(getTagBuildEnv("v1.2.3"));

        CiInformation ciInformation = createCiInformation(environment);

        assertTrue(ciInformation.isCi());
        assertNull(ciInformation.getBranch());
        assertEquals("12", ciInformation.getBuildNumber());
        assertFalse(ciInformation.isPullRequest());
        assertNull(ciInformation.getPullRequest());
        assertNull(ciInformation.getPullRequestTargetBranch());
        assertTrue(ciInformation.isTag());
        assertEquals("v1.2.3", ciInformation.getTag());
        assertEquals("v1.2.3", ciInformation.getReference());
    }

}
