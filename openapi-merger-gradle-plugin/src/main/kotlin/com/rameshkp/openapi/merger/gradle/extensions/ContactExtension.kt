package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 *  An extension class for contact
 */
open class ContactExtension @Inject constructor(objectFactory: ObjectFactory) {
    val name: Property<String> = objectFactory.property(String::class.java)
    val url: Property<String> = objectFactory.property(String::class.java)
    val email: Property<String> = objectFactory.property(String::class.java)
}