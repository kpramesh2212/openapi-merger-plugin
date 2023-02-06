package com.rameshkp.openapi.merger.gradle.plugin

import com.rameshkp.openapi.merger.gradle.extensions.OpenApiMergerExtension
import com.rameshkp.openapi.merger.gradle.task.OpenApiMergerTask
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.gradle.api.GradleException
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder


internal class OpenApiMergerGradlePluginTest: WordSpec({
    val builder = ProjectBuilder.builder()

    "Using the Plugin ID" should {
        "Apply the Plugin" {
            val project = builder.build()
            project.pluginManager.apply("com.rameshkp.openapi-merger-gradle-plugin")
            project.plugins.getPlugin(OpenApiMergerGradlePlugin::class.java) shouldNotBe null
        }
    }

    "Applying the Plugin" should {
        val project = builder.build()
        project.pluginManager.apply(OpenApiMergerGradlePlugin::class.java)

        "Register an extension with name 'openApiMerger'" {
            project.extensions.getByName("openApiMerger") shouldNotBe null
        }
        "Register a task with name 'mergeOpenApiFiles'" {
            val mergeOpenApiFilesTask = project.tasks.first() as Task
            mergeOpenApiFilesTask shouldNotBe null
            mergeOpenApiFilesTask.name shouldBe "mergeOpenApiFiles"
            mergeOpenApiFilesTask.description shouldBe "Task to merge multiple openapi files"
            mergeOpenApiFilesTask.group shouldBe "Open Api Merger"
        }
    }

    "Running the 'openApiMerger' task" should {
        val project = builder.build()
        project.pluginManager.apply(OpenApiMergerGradlePlugin::class.java)
        val openApiMergerExtension = project.extensions.getByName("openApiMerger") as OpenApiMergerExtension
        val openApiMerger = project.tasks.getByName("mergeOpenApiFiles") as OpenApiMergerTask
        openApiMergerExtension.inputDirectory.set(project.buildDir)

        "Throw exception when open api version is missing" {
            openApiMergerExtension.openApi.info.title.set("Title")
            openApiMergerExtension.openApi.info.version.set("version")
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe """
                Error merging openapi files. Reason=
                OpenApi version cannot be blank
            """.trimIndent()
        }
        "Throw exception when open api info title is missing" {
            openApiMergerExtension.openApi.openApiVersion.set("3.0.3")
            openApiMergerExtension.openApi.info.version.set("version")
            openApiMergerExtension.openApi.info.title.set("")
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe """
                Error merging openapi files. Reason=
                OpenApi Info Title cannot be blank
            """.trimIndent()
        }
        "Throw exception when open api info version is missing" {
            openApiMergerExtension.openApi.openApiVersion.set("3.0.3")
            openApiMergerExtension.openApi.info.title.set("Title")
            openApiMergerExtension.openApi.info.version.set("")
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe """
                Error merging openapi files. Reason=
                OpenApi Info Version cannot be blank
            """.trimIndent()
        }
    }

    "Running the 'openApiMerger' task with invalid file extension" should {
        "Throw exception" {
            val project = builder.build()
            project.pluginManager.apply(OpenApiMergerGradlePlugin::class.java)
            val openApiMergerExtension = project.extensions.getByName("openApiMerger") as OpenApiMergerExtension
            openApiMergerExtension.inputDirectory.set(project.buildDir)

            openApiMergerExtension.openApi.openApiVersion.set("3.0.3")
            openApiMergerExtension.openApi.info.title.set("Title")
            openApiMergerExtension.openApi.info.version.set("version")
            openApiMergerExtension.output.fileExtension.set("txt")

            val exception = shouldThrow<GradleException> {
                val openApiMerger = project.tasks.getByName("mergeOpenApiFiles") as OpenApiMergerTask
                openApiMerger.execute()
            }
            exception.message shouldBe "Could not create task ':mergeOpenApiFiles'."
        }
    }

})