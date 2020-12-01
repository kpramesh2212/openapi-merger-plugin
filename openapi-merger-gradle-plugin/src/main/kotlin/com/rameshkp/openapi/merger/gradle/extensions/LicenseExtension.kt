package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 *  An extension for license object
 */
open class LicenseExtension @Inject constructor(objectFactory: ObjectFactory) {
    val name: Property<String> = objectFactory.property(String::class.java)
    val url: Property<String> = objectFactory.property(String::class.java)
}