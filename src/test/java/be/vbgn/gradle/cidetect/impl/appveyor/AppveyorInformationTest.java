package be.vbgn.gradle.cidetect.impl.appveyor;

import static org.junit.Assert.assertEquals;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.impl.AbstractCiInformationTest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class AppveyorInformationTest extends AbstractCiInformationTest {

    @Override
    protected Map<String, String> getBaseEnv(String buildNumber) {
        Map<String, String> env = new HashMap<>();
        env.put("APPVEYOR", "True");
        env.put("APPVEYOR_BUILD_NUMBER", buildNumber);
        return env;
    }

    @Override
    protected Map<String, String> getBranchBuildEnv(String branch) {
        return Collections.singletonMap("APPVEYOR_REPO_BRANCH", branch);
    }

    @Override
    protected Map<String, String> getPullRequestBuildEnv(String sourceBranch, String targetBranch, String prNumber) {
        Map<String, String> environment = new HashMap<>();
        environment.put("APPVEYOR_PULL_REQUEST_HEAD_REPO_BRANCH", sourceBranch);
        environment.put("APPVEYOR_REPO_BRANCH", targetBranch);
        environment.put("APPVEYOR_PULL_REQUEST_NUMBER", prNumber);
        return environment;
    }

    @Override
    protected Map<String, String> getTagBuildEnv(String tagName) {
        Map<String, String> environment = new HashMap<>();
        environment.put("APPVEYOR_REPO_BRANCH", "some-branch-name");
        environment.put("APPVEYOR_REPO_TAG_NAME", tagName);
        environment.put("APPVEYOR_REPO_TAG", "true");
        return environment;
    }

    @Override
    protected CiInformation createCiInformation(Map<String, String> env) {
        return new AppveyorInformation(env);
    }

    @Test
    public void testPlatform() {
        assertEquals("Appveyor", new AppveyorInformation(getBaseEnv("12")).getPlatform());
    }
}
