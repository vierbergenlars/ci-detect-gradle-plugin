package be.vbgn.gradle.cidetect.impl.gitlab;

import java.util.Collections;
import java.util.Map;

public class LegacyGitlabCiInformationTest extends GitlabCiInformationTest {

    @Override
    protected Map<String, String> getBranchBuildEnv(String branch) {
        return Collections.singletonMap("CI_BUILD_REF_NAME", branch);
    }

    @Override
    protected Map<String, String> getTagBuildEnv(String tagName) {
        return Collections.singletonMap("CI_BUILD_TAG", tagName);
    }
}
