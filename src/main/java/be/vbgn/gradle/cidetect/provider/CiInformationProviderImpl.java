package be.vbgn.gradle.cidetect.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

final class CiInformationProviderImpl {

    private static List<CiInformationProvider> installedProviders = null;

    private static final Object lock = new Object();

    private CiInformationProviderImpl() {
    }


    static List<CiInformationProvider> installedProviders() {
        if (installedProviders == null) {
            ServiceLoader<CiInformationProvider> serviceLoader = ServiceLoader.load(CiInformationProvider.class);

            List<CiInformationProvider> providers = new ArrayList<>();
            for (CiInformationProvider ciInformationProvider : serviceLoader) {
                providers.add(ciInformationProvider);
            }
            synchronized (lock) {
                installedProviders = Collections.unmodifiableList(providers);
            }
        }
        return installedProviders;
    }


}
