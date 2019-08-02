package be.vbgn.gradle.cidetect.provider;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CiInformationProviderTest {
    @Test
    public void testInstalledProviders() {
        assertTrue("At least one provider should be available", !CiInformationProvider.installedProviders().isEmpty());
    }

}
