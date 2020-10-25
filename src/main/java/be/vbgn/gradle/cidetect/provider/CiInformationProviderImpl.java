package be.vbgn.gradle.cidetect.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import lombok.Synchronized;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

final class CiInformationProviderImpl {

    /**
     * Initialization on demand holder
     *
     * @see https://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom
     */
    private static final class LazyHolder {

        private static final CiInformationProviderImpl INSTANCE = new CiInformationProviderImpl();
    }

    private static final Logger LOGGER = Logging.getLogger(CiInformationProviderImpl.class);

    private List<CiInformationProvider> installedProviders;

    private CiInformationProviderImpl() {
    }

    @Synchronized
    private void initializeInstalledProviders() {
        if (installedProviders == null) {
            LOGGER.debug("Initializing providers for service {}", CiInformationProvider.class);
            ServiceLoader<CiInformationProvider> serviceLoader = ServiceLoader.load(CiInformationProvider.class);

            List<CiInformationProvider> providers = new ArrayList<>();
            for (CiInformationProvider ciInformationProvider : serviceLoader) {
                providers.add(ciInformationProvider);
            }

            LOGGER.debug("Found {} providers for service {}", providers.size(), CiInformationProvider.class);

            installedProviders = providers;
            resortProviders();
        }
    }

    @Synchronized
    private List<CiInformationProvider> getInstalledProviders() {
        initializeInstalledProviders();
        // Prevent additional providers from being installed after installed providers have been read once
        LOGGER.debug("Locking providers");
        installedProviders = Collections.unmodifiableList(installedProviders);
        return installedProviders;
    }

    private void resortProviders() {
        installedProviders.sort(Comparator.comparing(CiInformationProvider::getPriority).reversed());
        LOGGER.debug("Providers in query order: {}",
                installedProviders.stream().map(CiInformationProvider::getClass).collect(
                        Collectors.toList()));
    }

    private void registerAdditionalProvider(Class<? extends CiInformationProvider> clazz) {
        initializeInstalledProviders();
        LOGGER.debug("Registering additional provider {}", clazz);
        if (installedProviders.stream()
                .anyMatch(ciInformationProvider -> ciInformationProvider.getClass().equals(clazz))) {
            LOGGER.debug("Additional provider {} is already registered, not registering an additional instance", clazz);
            return;
        }

        try {
            installedProviders.add(clazz.newInstance());
            resortProviders();
            LOGGER.debug("Registered additional provider {}", clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServiceConfigurationError("Failed to register a new provider instance", e);
        }
    }

    static void registerProvider(Class<? extends CiInformationProvider> clazz) {
        LazyHolder.INSTANCE.registerAdditionalProvider(clazz);
    }

    static List<CiInformationProvider> installedProviders() {
        return LazyHolder.INSTANCE.getInstalledProviders();
    }
}
