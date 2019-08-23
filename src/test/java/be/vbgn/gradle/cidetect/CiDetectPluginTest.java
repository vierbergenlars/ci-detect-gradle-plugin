package be.vbgn.gradle.cidetect;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

public class CiDetectPluginTest {

    @Test
    public void apply() {
        Project project = ProjectBuilder.builder().build();
        new CiDetectPlugin().apply(project);

        assertNotNull(project.findProperty("ci"));
        assertTrue("ci is an instance of CiInformation", project.findProperty("ci") instanceof CiInformation);
    }
}
