package be.vbgn.gradle.cidetect.impl.appveyor;

import be.vbgn.gradle.cidetect.CiInformation;
import be.vbgn.gradle.cidetect.provider.CiInformationProvider;
import javax.annotation.Nullable;
import org.gradle.api.Project;

public class AppveyorInformationProvider implements CiInformationProvider {

    @Nullable
    @Override
    public CiInformation newCiInformation(@Nullable Project project) {
        return new AppveyorInformation(System.getenv());
    }
}
