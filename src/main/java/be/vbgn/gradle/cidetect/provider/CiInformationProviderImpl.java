package be.vbgn.gradle.cidetect.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

final class CiInformationProviderImpl {

    /**
     * Initialization on demand holder
     *
     * @see https://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom
     */
    private static final class LazyHolder {

        private static final CiInformationProviderImpl INSTANCE = new CiInformationProviderImpl();
    }

    private final List<CiInformationProvider> installedProviders;

    private CiInformationProviderImpl() {
        ServiceLoader<CiInformationProvider> serviceLoader = ServiceLoader.load(CiInformationProvider.class);

        List<CiInformationProvider> providers = new ArrayList<>();
        for (CiInformationProvider ciInformationProvider : serviceLoader) {
            providers.add(ciInformationProvider);
        }

        providers.sort(Comparator.comparing(CiInformationProvider::getPriority).reversed());
        installedProviders = Collections.unmodifiableList(providers);
    }

    private List<CiInformationProvider> getInstalledProviders() {
        return installedProviders;
    }


    static List<CiInformationProvider> installedProviders() {
        return LazyHolder.INSTANCE.getInstalledProviders();
    }
}
