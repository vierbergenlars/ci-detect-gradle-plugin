package be.vbgn.gradle.cidetect.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.gradle.api.Project;
import org.junit.Test;

public class CiInformationProviderRegisterTest {

    static class TestProvider implements CiInformationProvider {

        @Override
        public int getPriority() {
            return 10;
        }

        @Nullable
        @Override
        public CiInformation newCiInformation(@Nullable Project project) {
            return null;
        }
    }

    @Test
    public void testRegisterProvider() {
        CiInformationProvider.registerProvider(TestProvider.class);
        CiInformationProvider.registerProvider(TestProvider.class);

        List<CiInformationProvider> providers = CiInformationProvider.installedProviders();
        List<Class<? extends CiInformationProvider>> providerClasses = providers.stream()
                .map(CiInformationProvider::getClass).collect(Collectors.toList());

        assertSame(TestProvider.class, providerClasses.get(0));

        assertEquals("There are no providers registered multiple times", new HashSet<>(providerClasses).size(),
                providerClasses.size());
    }


}
