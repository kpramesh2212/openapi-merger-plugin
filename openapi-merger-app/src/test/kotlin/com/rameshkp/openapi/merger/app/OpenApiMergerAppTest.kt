package com.rameshkp.openapi.merger.app

import com.rameshkp.openapi.merger.app.exceptions.OpenApiDataInvalidException
import com.rameshkp.openapi.merger.app.models.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain


internal class OpenApiMergerAppTest: BehaviorSpec({
    val openApiMergerApp = OpenApiMergerApp()
    val inputDir = createTempDir(suffix = "input")
    val outputDir = createTempDir(suffix = "output")

    given("An open api object") {
        `when`("Its null") {
            then("should throw OpenApiDataInvalidException") {
                val e = shouldThrow<OpenApiDataInvalidException> {
                    openApiMergerApp.merge(inputDir, outputDir, null)
                }
                e.message shouldBe "OpenApi Model parameter cannot be null."
            }
        }

        `when`("Info object is absent") {
            then("should throw OpenApiDataInvalidationException") {
                val e = shouldThrow<OpenApiDataInvalidException> {
                    openApiMergerApp.merge(inputDir, outputDir, OpenApi())
                }
                e.message shouldContain "OpenApi Info object cannot be null"
            }
        }

        `when`("All sub-objects not null and all fields null") {
            then("should throw OpenApiDataInvalidationException") {
                val openApi = OpenApi()
                val info = Info()
                openApi.info = info
                openApi.externalDocs = ExternalDocs()
                info.contact = Contact()
                info.license = License()

                val e = shouldThrow<OpenApiDataInvalidException> {
                    openApiMergerApp.merge(inputDir, outputDir, openApi)
                }
                e.message shouldContain "OpenApi Info Version cannot be blank"
                e.message shouldContain "OpenApi Info Title cannot be blank"
                e.message shouldContain "OpenApi version cannot be blank"
                e.message shouldContain "License name cannot be blank"
                e.message shouldContain "ExternalDocs Url cannot be blank"
            }
        }

        `when`("Info object is present") {
            val openApi = OpenApi()
            openApi.version = "3.0.3"

            and("all fields are null") {
                then("should throw OpenApiDataInvalidationException") {
                    openApi.info = Info()
                    val e = shouldThrow<OpenApiDataInvalidException> {
                        openApiMergerApp.merge(inputDir, outputDir, openApi)
                    }
                    e.message shouldContain "OpenApi Info Version cannot be blank"
                    e.message shouldContain "OpenApi Info Title cannot be blank"
                }
            }

            and("title is null") {
                then("should throw OpenApiDataInvalidationException") {
                    val info = Info()
                    openApi.info = info
                    info.version = "3.3.3"
                    val e = shouldThrow<OpenApiDataInvalidException> {
                        openApiMergerApp.merge(inputDir, outputDir, openApi)
                    }
                    e.message shouldBe "OpenApi Info Title cannot be blank"
                }
            }

            and("version is null") {
                then("should throw OpenApiDataInvalidationException") {
                    val info = Info()
                    openApi.info = info
                    info.title = "A valid title"
                    val e = shouldThrow<OpenApiDataInvalidException> {
                        openApiMergerApp.merge(inputDir, outputDir, openApi)
                    }
                    e.message shouldBe "OpenApi Info Version cannot be blank"
                }
            }
        }

    }
})