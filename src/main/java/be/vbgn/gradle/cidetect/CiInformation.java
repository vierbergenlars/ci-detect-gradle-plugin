package be.vbgn.gradle.cidetect;

import be.vbgn.gradle.cidetect.provider.CiInformationProvider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

final class LoggerHolder {

    static final Logger LOGGER = Logging.getLogger(CiInformation.class);

    private LoggerHolder() {
    }
}

public interface CiInformation {

    /**
     * Detects if the build is running in a CI environment
     *
     * @return Build information from the CI platform
     */
    @Nonnull
    static CiInformation detect() {
        return detect(null);
    }

    /**
     * Detects if the build is running in a CI environment
     *
     * @param project The gradle project that is checked. May be null if no project is available.
     * @return Build information from the CI platform
     */
    @Nonnull
    static CiInformation detect(@Nullable Project project) {
        LoggerHolder.LOGGER.debug("Performing CI detection for {}", project);
        for (CiInformationProvider installedProvider : CiInformationProvider.installedProviders()) {
            LoggerHolder.LOGGER.debug("Querying provider {}", installedProvider);
            if (installedProvider.isSupported()) {
                CiInformation ciInformation = installedProvider.newCiInformation(project);
                LoggerHolder.LOGGER.debug("Information from provider {}: {}", installedProvider, ciInformation);
                if (ciInformation != null && ciInformation.isCi()) {
                    LoggerHolder.LOGGER.debug("Using CI information for {}: {}", project, ciInformation);
                    return ciInformation;
                }
            }
        }

        LoggerHolder.LOGGER.debug("No CI information found for {}. Using a null result.", project);

        return new NullCiInformation();
    }

    /**
     * @return If the build is running in a CI environment
     */
    boolean isCi();

    /**
     * @return A unique identifier of the build. Usually a sequential buildnumber, but can be anything depending on the CI environment
     */
    @Nullable
    String getBuildNumber();

    /**
     * @return The current branch for which a build is being executed
     */
    @Nullable
    String getBranch();

    /**
     * @return The SCM reference that is currently being built. Either a tag or a branch, depending on what is being built
     */
    @Nullable
    default String getReference() {
        return isTag() ? getTag() : getBranch();
    }

    /**
     * @return If the current build is a pull request
     */
    default boolean isPullRequest() {
        return getPullRequest() != null;
    }

    /**
     * @return A unique identifier of the pull request. Usually a sequential number, but can be anything depending on the SCM platform
     */
    @Nullable
    String getPullRequest();

    /**
     * @return The branch where the pull request will be merged into if it is merged
     */
    @Nullable
    String getPullRequestTargetBranch();

    /**
     * @return If the current build is a build of a tag
     */
    default boolean isTag() {
        return getTag() != null;
    }

    /**
     * @return The current tag for which a build is being executed
     */
    @Nullable
    String getTag();

    /**
     * @return A string that identifies the CI platform that the build is being run on
     */
    @Nullable
    default String getPlatform() {
        String className = getClass().getSimpleName();
        if (className.endsWith("Information")) {
            return className.substring(0, className.length() - "Information".length());
        }
        return className;
    }
}
