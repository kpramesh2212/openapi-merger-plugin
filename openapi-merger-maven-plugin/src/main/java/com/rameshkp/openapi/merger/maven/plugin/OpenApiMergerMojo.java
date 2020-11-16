package com.rameshkp.openapi.merger.maven.plugin;

import com.rameshkp.openapi.merger.app.OpenApiMergerApp;
import com.rameshkp.openapi.merger.app.models.OpenApi;
import com.rameshkp.openapi.merger.maven.models.OpenApiFileFormat;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "merge")
public class OpenApiMergerMojo extends AbstractMojo {
    /**
     * Input directory containing openapi schema files
     */
    @Parameter
    private File inputDir;

    /**
     * Output directory to place the merged schema file
     */
    @Parameter(defaultValue = "target/open-api")
    private File outputDir;

    /**
     * Name of the merged file
     */
    @Parameter(defaultValue = "openapi")
    private String outputFileName;

    /**
     *  Output format of the merged file
     */
    @Parameter(defaultValue = "YAML")
    private OpenApiFileFormat outputFileFormat;

    /**
     *  OpenApiInfo Object
     */
    @Parameter
    private OpenApi openApi;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log  log = getLog();

        log.debug("Starting validation");
        validate();

        File outputFile = getOutputFile(log);

        log.debug("Attempting to merge the open api schema files");
        OpenApiMergerApp apiMergerApp = new OpenApiMergerApp();
        try {
            apiMergerApp.run(inputDir, outputFile, openApi);
            log.info("Completed Open API file merging. output " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            log.error(e);
            throw new MojoFailureException(e.getMessage(), e);
        }
    }

    private File getOutputFile(Log log) {
        boolean created = outputDir.mkdirs();
        log.debug("Create the output directory " + created);

        String fileExtension = "";
        if (outputFileFormat == OpenApiFileFormat.JSON) {
            fileExtension = ".json";
        } else {
            fileExtension = ".yaml";
        }
        String outFileWithExtension = outputFileName + fileExtension;
        return new File(outputDir, outFileWithExtension);
    }

    // Later do this via validators
    private void validate() throws MojoFailureException {
        if (inputDir == null || !inputDir.isDirectory()) {
            throw new MojoFailureException("Invalid InputDir: Provide a valid directory containing open api schema files");
        }
    }
}
