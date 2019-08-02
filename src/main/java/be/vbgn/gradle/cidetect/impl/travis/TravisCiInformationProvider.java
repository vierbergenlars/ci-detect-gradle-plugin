package be.vbgn.gradle.cidetect.impl.travis;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.provider.CiInformationProvider;
import javax.annotation.Nullable;
import org.gradle.api.Project;

class TravisCiInformationProvider implements CiInformationProvider {

    @Override
    public boolean isSupported() {
        return true;
    }

    @Nullable
    @Override
    public CiInformation newCiInformation(@Nullable Project project) {
        return new TravisCiInformation(System.getenv());
    }
}
