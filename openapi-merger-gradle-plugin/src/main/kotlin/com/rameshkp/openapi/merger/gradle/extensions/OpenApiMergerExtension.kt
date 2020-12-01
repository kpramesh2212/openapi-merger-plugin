package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

/**
 *  An extension for open api merger object
 */
open class OpenApiMergerExtension @Inject constructor(objectFactory: ObjectFactory) {
    val inputDirectory: DirectoryProperty = objectFactory.directoryProperty()
    internal val openApi: OpenApiExtension = objectFactory.newInstance(OpenApiExtension::class.java, objectFactory)
    internal val output: OutputExtension = objectFactory.newInstance(OutputExtension::class.java, objectFactory)

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