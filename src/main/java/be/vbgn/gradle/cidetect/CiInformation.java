package be.vbgn.gradle.cidetect;

import be.vbgn.gradle.cidetect.provider.CiInformationProvider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.gradle.api.Project;

public interface CiInformation {

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

    boolean isCi();

}
