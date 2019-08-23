package be.vbgn.gradle.cidetect;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CiInformationTest {

    @Test
    public void detect() {
        assertNotNull(CiInformation.detect());
    }

}
