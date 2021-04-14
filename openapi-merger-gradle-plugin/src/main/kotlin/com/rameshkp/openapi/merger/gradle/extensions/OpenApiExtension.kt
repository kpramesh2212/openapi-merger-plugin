package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 *  An extension for open api object
 */
open class OpenApiExtension @Inject constructor(project: Project) {
    val openApiVersion: Property<String> = project.objects.property(String::class.java)
    val info: InfoExtension = project.objects.newInstance(InfoExtension::class.java)
    internal val externalDocs: ExternalDocsExtension = project.objects.newInstance(ExternalDocsExtension::class.java)
    val servers: NamedDomainObjectContainer<ServerExtension> = project.container(ServerExtension::class.java) {
        ServerExtension(it, project.objects)
    }

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