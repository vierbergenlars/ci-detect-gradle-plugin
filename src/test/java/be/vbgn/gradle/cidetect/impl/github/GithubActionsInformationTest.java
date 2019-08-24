package be.vbgn.gradle.cidetect.impl.github;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.impl.AbstractCiInformationTest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GithubActionsInformationTest extends AbstractCiInformationTest {

    @Override
    protected Map<String, String> getBaseEnv(String buildNumber) {
        Map<String, String> env = new HashMap<>();
        env.put("GITHUB_ACTIONS", "true");
        env.put("GITHUB_SHA", buildNumber);
        env.put("GITHUB_BASE_REF", "");
        env.put("GITHUB_HEAD_REF", "");
        return env;
    }

    @Override
    protected Map<String, String> getBranchBuildEnv(String branch) {
        return Collections.singletonMap("GITHUB_REF", "refs/heads/" + branch);
    }

    @Override
    protected Map<String, String> getPullRequestBuildEnv(String sourceBranch, String targetBranch, String prNumber) {
        Map<String, String> env = new HashMap<>();
        env.put("GITHUB_BASE_REF", targetBranch);
        env.put("GITHUB_HEAD_REF", sourceBranch);
        env.put("GITHUB_REF", "refs/pull/" + prNumber + "/merge");
        return env;
    }

    @Override
    protected Map<String, String> getTagBuildEnv(String tagName) {
        return Collections.singletonMap("GITHUB_REF", "refs/tags/" + tagName);
    }

    @Override
    protected CiInformation createCiInformation(Map<String, String> env) {
        return new GithubActionsInformation(env);
    }
}
