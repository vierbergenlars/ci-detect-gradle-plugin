package be.vbgn.gradle.cidetect;

import be.vbgn.gradle.cidetect.provider.CiInformationProvider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.gradle.api.Project;

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
        for (CiInformationProvider installedProvider : CiInformationProvider.installedProviders()) {
            if (installedProvider.isSupported()) {
                CiInformation ciInformation = installedProvider.newCiInformation(project);
                if (ciInformation != null && ciInformation.isCi()) {
                    return ciInformation;
                }
            }
        }

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
}
