package be.vbgn.gradle.cidetect.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

class CiInformationProviderImpl {
    private static List<CiInformationProvider> installedProviders = null;

    public static List<CiInformationProvider> installedProviders() {
        if(installedProviders == null) {
            ServiceLoader<CiInformationProvider> serviceLoader = ServiceLoader.load(
                    CiInformationProvider.class);

            installedProviders = new ArrayList<>();

            for (CiInformationProvider ciInformationProvider : serviceLoader) {
                installedProviders.add(ciInformationProvider);
            }
        }
        return installedProviders;
    }


}
