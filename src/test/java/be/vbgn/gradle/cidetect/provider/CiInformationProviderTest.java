package be.vbgn.gradle.cidetect.provider;

import static org.junit.Assert.assertTrue;

import be.vbgn.gradle.cidetect.CiInformation;
import javax.annotation.Nullable;
import org.gradle.api.Project;
import org.junit.Test;

public class CiInformationProviderTest {

    static class TestProvider implements CiInformationProvider {

        @Nullable
        @Override
        public CiInformation newCiInformation(@Nullable Project project) {
            return null;
        }
    }

    @Test
    public void testInstalledProviders() {
        assertTrue("At least one provider should be available", !CiInformationProvider.installedProviders().isEmpty());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testRegisterProviderAfterFetching() {
        CiInformationProvider.installedProviders();

        CiInformationProvider.registerProvider(TestProvider.class);
    }

}
