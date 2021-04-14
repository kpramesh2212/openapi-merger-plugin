package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class ServerExtension @Inject constructor(val name: String, objectFactory: ObjectFactory) {
    val url: Property<String> = objectFactory.property(String::class.java)
    val description: Property<String> = objectFactory.property(String::class.java)
}