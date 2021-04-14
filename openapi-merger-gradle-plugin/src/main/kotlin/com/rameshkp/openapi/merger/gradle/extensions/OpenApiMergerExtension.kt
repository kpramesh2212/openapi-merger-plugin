package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import javax.inject.Inject

/**
 *  An extension for open api merger object
 */
open class OpenApiMergerExtension @Inject constructor(project: Project) {
    val inputDirectory: DirectoryProperty = project.objects.directoryProperty()
    internal val openApi: OpenApiExtension = project.objects.newInstance(OpenApiExtension::class.java, project)
    internal val output: OutputExtension = project.objects.newInstance(OutputExtension::class.java, project.objects)

    /**
     *  Invoke the openApi extension action
     */
    fun openApi(action: Action<OpenApiExtension>) {
        action.execute(openApi)
    }

    /**
     *  Invoke the output extension
     */
    fun output(action: Action<OutputExtension>) {
        action.execute(output)
    }
}