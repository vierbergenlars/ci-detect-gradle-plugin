package be.vbgn.gradle.cidetect;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CiDetectPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        CiInformation ciInformation = CiInformation.detect(project);

        project.getExtensions().add("ci", ciInformation);

    }
}
