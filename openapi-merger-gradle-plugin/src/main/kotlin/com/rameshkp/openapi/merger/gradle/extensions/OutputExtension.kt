package com.rameshkp.openapi.merger.gradle.extensions

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class OutputExtension @Inject constructor(objectFactory: ObjectFactory) {
    val directory: DirectoryProperty = objectFactory.directoryProperty()
    val fileName: Property<String> = objectFactory.property(String::class.java)
    val fileExtension: Property<String> = objectFactory.property(String::class.java)
}