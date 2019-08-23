package be.vbgn.gradle.cidetect.impl.jenkins;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.impl.AbstractCiInformationTest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Ignore;

public class JenkinsInformationTest extends AbstractCiInformationTest {

    @Override
    protected Map<String, String> getBaseEnv(String buildNumber) {
        Map<String, String> env = new HashMap<>();
        env.put("JENKINS_HOME", "/home/jenkins");
        env.put("BUILD_NUMBER", buildNumber);
        return env;
    }

    @Override
    protected Map<String, String> getBranchBuildEnv(String branch) {
        return Collections.singletonMap("BRANCH_NAME", branch);
    }

    @Override
    protected Map<String, String> getPullRequestBuildEnv(String sourceBranch, String targetBranch, String prNumber) {
        Map<String, String> environment = new HashMap<>();
        environment.put("BRANCH_NAME", sourceBranch);
        environment.put("CHANGE_TARGET", targetBranch);
        environment.put("CHANGE_ID", prNumber);
        return environment;
    }

    @Override
    protected Map<String, String> getTagBuildEnv(String tagName) {
        throw new AssumptionViolatedException("Tags are not supported on jenkins.");
    }

    @Override
    protected CiInformation createCiInformation(Map<String, String> env) {
        return new JenkinsInformation(env);
    }
}
