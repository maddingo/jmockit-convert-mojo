package info.goldhahn.mojo;

import mockit.coverage.data.CoverageData;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

/**
 * Converts a jmockit serial file into Sonar Qube's Generic XML coverage format.
 */
@Mojo(name = "sonarxml", defaultPhase = LifecyclePhase.PACKAGE)
public class JmockitConvertMojo extends AbstractMojo {

    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}/coverage.ser", required = true)
    private File jmockitDataFile;

    /**
     * The output file.
     */
    @Parameter(defaultValue = "${project.build.directory}/coverage.xml", required = true)
    private File outputFile;

    public void execute() throws MojoExecutionException {
        if (jmockitDataFile == null || !jmockitDataFile.canRead()) {
            throw new MojoExecutionException("Cannot read Data File " + jmockitDataFile);
        }
        if (outputFile == null) {
            throw new MojoExecutionException("outputFile is null");
        }
        try {
            CoverageData data = CoverageData.readDataFromFile(jmockitDataFile);
            createDirectory(outputFile);
            new SonarXmlCoverage(outputFile, data, getLog()).generate();
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot execute mojo with file " + jmockitDataFile, e);
        }
    }

    private void createDirectory(File outputFile) {
        outputFile.getParentFile().mkdirs();
    }

    /**
     * For testing.
     */
    void setJmockitDataFile(File jmockitDataFile) {
        this.jmockitDataFile = jmockitDataFile;
    }

    /**
     * For testing.
     */
    void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
}
