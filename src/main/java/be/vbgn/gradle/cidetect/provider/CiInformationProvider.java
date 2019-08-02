package be.vbgn.gradle.cidetect.provider;

import be.vbgn.gradle.cidetect.CiInformation;
import java.util.List;
import javax.annotation.Nullable;
import org.gradle.api.Project;

public interface CiInformationProvider {

    static List<CiInformationProvider> installedProviders() {
        return CiInformationProviderImpl.installedProviders();
    }

    boolean isSupported();

    @Nullable
    CiInformation newCiInformation(@Nullable Project project);
}
