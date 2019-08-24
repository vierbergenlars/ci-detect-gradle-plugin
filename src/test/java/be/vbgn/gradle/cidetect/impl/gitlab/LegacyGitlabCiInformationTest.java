package be.vbgn.gradle.cidetect.impl.gitlab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LegacyGitlabCiInformationTest extends GitlabCiInformationTest {

    @Override
    protected Map<String, String> getBranchBuildEnv(String branch) {
        return Collections.singletonMap("CI_BUILD_REF_NAME", branch);
    }

    @Override
    protected Map<String, String> getTagBuildEnv(String tagName) {
        Map<String, String> env = new HashMap<>();
        env.put("CI_BUILD_REF_NAME", tagName);
        env.put("CI_BUILD_TAG", tagName);
        return env;
    }
}
