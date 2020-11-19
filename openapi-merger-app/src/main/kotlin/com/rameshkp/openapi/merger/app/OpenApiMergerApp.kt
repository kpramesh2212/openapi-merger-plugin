package com.rameshkp.openapi.merger.app

import com.rameshkp.openapi.merger.app.exceptions.OpenApiDataInvalidException
import com.rameshkp.openapi.merger.app.mergers.OpenApiMerger
import com.rameshkp.openapi.merger.app.models.OpenApi
import com.rameshkp.openapi.merger.app.utils.OpenAPIConverter

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.ParseOptions
import org.slf4j.LoggerFactory
import java.io.File
import java.util.stream.Collectors
import javax.validation.Validation

/**
 *  The class that helps in merging multiple open api v3 files into a single file
 */
class OpenApiMergerApp {
    private val log = LoggerFactory.getLogger(javaClass)
    private val validFileExtension = listOf("yaml", "json", "yml")
    private val parseOptions = ParseOptions()
    private val openApiMerger = OpenApiMerger()

    fun run(inputDir: File, outputFile: File, openApi: OpenApi?) {
        validate(openApi)
        inputDir.walk().filter { 
            validFileExtension.contains(it.extension)
        }.forEach {
            log.debug("Parsing OpenAPI file {}", it.absolutePath)
            val openAPI = OpenAPIV3Parser().read(it.absolutePath, null, parseOptions)
            openApiMerger.merge(openAPI)
        }
        val merged = openApiMerger.get()
        merged?.run {
            log.debug("Constructing the OpenApi model")
            // Set the openapi model object values to merged file
            openApi?.let { openApiModel ->
                openapi = openApiModel.version
                // Set the info object
                info = openApiModel.info?.let { infoModelObj ->
                    val info = Info()
                    info.title = infoModelObj.title
                    info.version = infoModelObj.version
                    info.description = infoModelObj.description
                    info.termsOfService = infoModelObj.termsOfService

                    // set contact
                    info.contact = infoModelObj.contact?.let { contactModelObj ->
                        val contact = Contact()
                        contact.url = contactModelObj.url
                        contact.email = contactModelObj.email
                        contact.name = contactModelObj.name
                        contact
                    }

                    // Set license
                    info.license = infoModelObj.license?.let { licenseModelObj ->
                        val license = License()
                        license.url = licenseModelObj.url
                        license.name = licenseModelObj.name
                        license
                    }
                    info
                }
                externalDocs = openApiModel.externalDocs?.let { externalDocsModelObj ->
                    val externalDocs = ExternalDocumentation()
                    externalDocs.url = externalDocsModelObj.url
                    externalDocs.description = externalDocsModelObj.description
                    externalDocs
                }
            }

            // Get convert the object and get it as string
            val out = when(outputFile.extension) {
                "json" -> OpenAPIConverter.toJson(this)
                "yaml", "yml" -> OpenAPIConverter.toYaml(this)
                else -> ""
            }

            // Write it the output file
            log.debug("Writing the merged output file {}", outputFile.absolutePath)
            outputFile.writeText(out, Charsets.UTF_8)
        }
    }

    private fun validate(openApi: OpenApi?) {
        if (openApi == null) {
            throw OpenApiDataInvalidException("OpenApi Model parameter cannot be null.")
        }
        val validationFactory = Validation.buildDefaultValidatorFactory()
        val errorMessage = validationFactory.validator.validate(openApi)
                .stream()
                .map { a -> a.message}
                .collect(Collectors.joining("\n"))
        if (!errorMessage.isNullOrBlank()) {
            log.error("Validation error has occurred. errorMessage {}", this)
            throw OpenApiDataInvalidException(errorMessage)
        }
    }

}