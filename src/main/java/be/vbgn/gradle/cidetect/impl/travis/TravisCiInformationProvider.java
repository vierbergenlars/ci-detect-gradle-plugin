package be.vbgn.gradle.cidetect.impl.travis;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.provider.CiInformationProvider;
import javax.annotation.Nullable;
import org.gradle.api.Project;

public class TravisCiInformationProvider implements CiInformationProvider {

    @Nullable
    @Override
    public CiInformation newCiInformation(@Nullable Project project) {
        return new TravisCiInformation(System.getenv());
    }
}
