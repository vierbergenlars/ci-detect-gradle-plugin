package be.vbgn.gradle.cidetect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Nullable;
import org.junit.Test;

public class CiInformationTest {

    private static class PlatformNameTest implements CiInformation {

        @Override
        public boolean isCi() {
            return false;
        }

        @Nullable
        @Override
        public String getBuildNumber() {
            return null;
        }

        @Nullable
        @Override
        public String getBranch() {
            return null;
        }

        @Nullable
        @Override
        public String getPullRequest() {
            return null;
        }

        @Nullable
        @Override
        public String getPullRequestTargetBranch() {
            return null;
        }

        @Nullable
        @Override
        public String getTag() {
            return null;
        }
    }

    @Test
    public void detect() {
        assertNotNull(CiInformation.detect());
    }

    @Test
    public void testPlatformNameWithCustomClass() {
        CiInformation information = new PlatformNameTest();
        assertEquals("PlatformNameTest", information.getPlatform());
    }

}
