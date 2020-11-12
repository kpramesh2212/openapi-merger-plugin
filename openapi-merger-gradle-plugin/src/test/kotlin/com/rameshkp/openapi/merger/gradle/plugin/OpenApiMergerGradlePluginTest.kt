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
    val project = ProjectBuilder.builder().build()


    "Using the Plugin ID" should {
        "Apply the Plugin" {
            project.pluginManager.apply("com.ramesh.openapi-merger-gradle-plugin")
            project.plugins.getPlugin(OpenApiMergerGradlePlugin::class.java) shouldNotBe null
        }
    }

    "Applying the Plugin" should {
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
        project.pluginManager.apply(OpenApiMergerGradlePlugin::class.java)
        val openApiMergerExtension = project.extensions.getByName("openApiMerger") as OpenApiMergerExtension
        val openApiMerger = project.tasks.getByName("mergeOpenApiFiles") as OpenApiMergerTask

        "Throw exception when open api version is missing" {
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe "Required field open api version missing"
        }
        "Throw exception when open api info title is missing" {
            openApiMergerExtension.openApi.openApiVersion.set("3.0.3")
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe "Required field open api info title missing"
        }
        "Throw exception when open api info version is missing" {
            openApiMergerExtension.openApi.openApiVersion.set("3.0.3")
            openApiMergerExtension.openApi.info.title.set("Title")
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe "Required field open api info version missing"
        }
        "Throw exception when output file extension is invalid" {
            openApiMergerExtension.openApi.openApiVersion.set("3.0.3")
            openApiMergerExtension.openApi.info.title.set("Title")
            openApiMergerExtension.openApi.info.version.set("version")
            openApiMergerExtension.output.fileExtension.set("txt")
            val exception = shouldThrow<GradleException> {
                openApiMerger.execute()
            }
            exception.message shouldBe "Invalid file extension txt. Valid values are [yaml, json, yml]"
        }
    }

})
