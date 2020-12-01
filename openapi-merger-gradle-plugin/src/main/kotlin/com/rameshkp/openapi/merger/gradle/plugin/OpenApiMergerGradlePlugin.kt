package com.rameshkp.openapi.merger.gradle.plugin

import com.rameshkp.openapi.merger.gradle.extensions.OpenApiMergerExtension
import com.rameshkp.openapi.merger.gradle.task.OpenApiMergerTask
import com.rameshkp.openapi.merger.gradle.utils.OPENAPI_EXTENSION_NAME
import com.rameshkp.openapi.merger.gradle.utils.OPENAPI_TASK_NAME
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  The main plugin
 *
 *  Creates an extension and a task
 */
class OpenApiMergerGradlePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(OPENAPI_EXTENSION_NAME, OpenApiMergerExtension::class.java, project.objects)
        project.tasks.register(OPENAPI_TASK_NAME, OpenApiMergerTask::class.java)
    }
}