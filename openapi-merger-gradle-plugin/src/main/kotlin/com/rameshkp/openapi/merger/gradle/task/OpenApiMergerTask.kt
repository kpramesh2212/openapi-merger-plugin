package com.rameshkp.openapi.merger.gradle.task

import com.rameshkp.openapi.merger.app.OpenApiMergerApp
import com.rameshkp.openapi.merger.app.exceptions.OpenApiDataInvalidException
import com.rameshkp.openapi.merger.app.models.*
import com.rameshkp.openapi.merger.gradle.extensions.OpenApiMergerExtension
import com.rameshkp.openapi.merger.gradle.utils.OPENAPI_EXTENSION_NAME
import com.rameshkp.openapi.merger.gradle.utils.OPENAPI_GROUP_NAME
import com.rameshkp.openapi.merger.gradle.utils.OPENAPI_TASK_DESCRIPTION
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

/**
 * Open Api Merger task to merge open api files
 */
open class OpenApiMergerTask : DefaultTask() {
    @get:SkipWhenEmpty
    @get:InputDirectory
    val inputDirectory: DirectoryProperty = project.objects.directoryProperty()
    @get:OutputFile
    val outputFileProperty: RegularFileProperty = project.objects.fileProperty()
    private val openApiMergerExtension: OpenApiMergerExtension

    private val validFileExtension = listOf("yaml", "json", "yml")

    private val defaultOutputDir: Provider<Directory> = project.layout.buildDirectory.dir("open-api")
    private val defaultOutputFileName = "openapi"
    private val defaultOutputFileExtension = "yaml"

    init {
        group = OPENAPI_GROUP_NAME
        description = OPENAPI_TASK_DESCRIPTION
        openApiMergerExtension = project.extensions.run {
            getByName(OPENAPI_EXTENSION_NAME) as OpenApiMergerExtension
        }
        inputDirectory.set(openApiMergerExtension.inputDirectory)
        validateAndSetValues()
    }

    private fun validateAndSetValues() {
        val outputFileExtension = openApiMergerExtension.output.fileExtension
        if (!outputFileExtension.isPresent) {
            outputFileExtension.set(defaultOutputFileExtension)
        }
        if (!validFileExtension.contains(outputFileExtension.get())) {
            throw GradleException("Invalid file extension ${outputFileExtension.get()}. Valid values are $validFileExtension")
        }
        // set the output
        val outputDir = openApiMergerExtension.output.directory.getOrElse(defaultOutputDir.get())
        val outputFileName = openApiMergerExtension.output.fileName.getOrElse(defaultOutputFileName)

        outputFileProperty.set(project.file("${outputDir.asFile}/$outputFileName.${outputFileExtension.get()}"))
    }

    @TaskAction
    fun execute() {

        val outputFile = outputFileProperty.asFile.get()
        outputFile.parentFile.mkdirs()

        // Construct your model.
        val openApiExtension = openApiMergerExtension.openApi
        val openApi = OpenApi().run {
            version = openApiExtension.openApiVersion.orNull

            info = Info().let { info ->
                val infoExtension = openApiExtension.info
                info.title = infoExtension.title.orNull
                info.description = infoExtension.description.orNull
                info.version = infoExtension.version.orNull
                info.termsOfService = infoExtension.termsOfService.orNull

                val contactUrl = infoExtension.contact.url.orNull
                val contactName = infoExtension.contact.name.orNull
                val contactEmail = infoExtension.contact.email.orNull
                if (isAnyValueNotNull(arrayOf(contactUrl, contactName, contactEmail))) {
                    info.contact = Contact().let { contact ->
                        contact.url = contactUrl
                        contact.email = contactEmail
                        contact.name = contactName
                        contact
                    }
                }

                val licenseName = infoExtension.license.name.orNull
                val licenseUrl = infoExtension.license.url.orNull
                if (isAnyValueNotNull(arrayOf(licenseName, licenseUrl))) {
                    info.license = License().let { license ->
                        license.name = licenseName
                        license.url = licenseUrl
                        license
                    }
                }

                info
            }

            val externalDocsUrl = openApiExtension.externalDocs.url.orNull
            val externalDocsDescription = openApiExtension.externalDocs.description.orNull
            if (isAnyValueNotNull(arrayOf(externalDocsUrl, externalDocsDescription))) {
                externalDocs =  ExternalDocs().let { eDocs ->
                    eDocs.url = externalDocsUrl
                    eDocs.description = externalDocsDescription
                    eDocs
                }
            }

            val servers = ArrayList<Server>()
            openApiExtension.servers.all {
                servers.add(Server(it.url.orNull, it.description.orNull))
            }

            this.servers = servers

            this
        }

        // external docs
        val openApiMergerApp = OpenApiMergerApp()
        try {
            openApiMergerApp.merge(
                inputDir = inputDirectory.get().asFile,
                outputFile = outputFile,
                openApi = openApi
            )
        } catch (e: OpenApiDataInvalidException) {
            logger.error("Error merging openapi files", e)
            throw GradleException("Error merging openapi files. Reason=\n${e.message}")
        }
    }

    private fun isAnyValueNotNull(array: Array<in Any?>): Boolean {
        return array.filterNotNull().isNotEmpty()
    }
}