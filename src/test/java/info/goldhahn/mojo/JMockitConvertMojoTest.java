package info.goldhahn.mojo;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class JMockitConvertMojoTest {
    @Rule
    public MojoRule rule = new MojoRule();

    @Rule
    public TestResources resources = new TestResources();

    @Test
    public void generateXml() throws Exception {

        File minimalProject = resources.getBasedir("minimal");
        File pom = new File(minimalProject, "pom.xml");
        assertTrue(pom.canRead());

        JmockitConvertMojo mojo = (JmockitConvertMojo) rule.lookupMojo("sonarxml", pom);
        mojo.setJmockitDataFile(new File(minimalProject, "testcoverage.ser"));
        File outputFile = new File(minimalProject, "testcoverage.xml");
        mojo.setOutputFile(outputFile);
        mojo.execute();

        assertTrue(outputFile.exists());
    }
}
