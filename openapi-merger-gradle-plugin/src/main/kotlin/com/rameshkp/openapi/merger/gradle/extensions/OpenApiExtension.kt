package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 *  An extension for open api object
 */
open class OpenApiExtension @Inject constructor(objectFactory: ObjectFactory) {
    val openApiVersion: Property<String> = objectFactory.property(String::class.java)
    val info: InfoExtension = objectFactory.newInstance(InfoExtension::class.java)
    internal val externalDocs: ExternalDocsExtension = objectFactory.newInstance(ExternalDocsExtension::class.java, objectFactory)

    /**
     *  Invoke the info extension
     */
    fun info(action: Action<InfoExtension>) {
        action.execute(info)
    }

    /**
     *  Invoke the external Docs extension
     */
    fun externalDocs(action: Action<ExternalDocsExtension>) {
        action.execute(externalDocs)
    }
}