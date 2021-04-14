# **Introduction to openapi-merger-gradle-plugin**

Open API Merger Gradle Plugin - Merges multiple OpenAPI-3 specification file into a single file.

This Gradle plugin provides the capability to merge multiple open api v3 specification file into a single file from a Gradle build. 

Compatibility Notes
-------------------

The plugin is build on gradle version 6.7

The Java version used to compile the plugin 1.8 

How To Use
----------

Gradle Groovy DSL

```groovy
plugins {
      id "com.rameshkp.openapi-merger-gradle-plugin" version "1.0.0"
}
```

Gradle Kotlin DSL
```kotlin
plugins {
    id("com.rameshkp.openapi-merger-gradle-plugin") version "1.0.0"
}
```

Note: For latest versions of the plugins please check the gradle plugin portal

What does this plugin do
-----------------------

[open api merger plugin readme](https://github.com/kpramesh2212/openapi-merger-plugin/blob/main/README.md)

How the plugin works
------------

When the user adds this plugin to their build file 

The plugin creates a task and an extension

1. mergeOpenApiFiles - Task

2. openApiMerger - Extension

Running the task mergeOpenApiFiles will merge multiple openapi v3 files into a single file

```bash
gradle mergeOpenApiFiles
``` 

Using openApiMerger extension users can perform customization as explained below

Customization
-------------

The following customizations can be done on the task mergeOpenApiFiles using extension openApiMerger as follows

```kotlin
openApiMerger {
    inputDirectory.set(file("openapi-files"))

    output {
        directory.set(buildDir)
        fileName.set("openapi")
        fileExtension.set("json")
    }

    openApi {
        openApiVersion.set("3.0.1")
        info {
            title.set("Open API Merger")
            description.set("All files merged by open api merger")
            version.set("1.0.0-SNAPSHOT")
            termsOfService.set("http://openapimerger.com/terms-of-service")
            contact {
                name.set("OpenApiMerger Team")
                email.set("openapi@sample.com")
                url.set("http://openapimerger.com")
            }
            license {
                name.set("Apache License v2.0")
                url.set("http://apache.org/v2")
            }
        }
        externalDocs {
            description.set("External docs description")
            url.set("http://external-docs.com/uri")
        }
        servers {
            register("production") {
                url.set("https://prod.com:9090")
                description.set("production environment")
            }
            register("testing") {
                url.set("https://localhost:8080")
                description.set("test environment")
            }
        }
    }
}
```
#### openApiMerger block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`inputDirectory` |  The input directory containing the openapi v3 specification files. The input directory can contain files in both yaml and json format. | Yes | N/A
`output(Block)` | The output block is used to customize the output of the open api merger plugin | No | Check output block
`openApi(Block)` | The openApi block is used to customize the common openApi object for the merged file. | Yes | N/A

#### output block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`directory` |  The output directory to place the merged open api v3 specification file. | No | build/open-api/
`fileName` | Name of the merged output openapi v3 specification file | No | openapi
`fileExtension` | The format of the output. Can either be one of yaml or json | No | yaml

#### openApi block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`openApiVersion` |  The version of open api. | Yes | N/A
`info(block)` | [See Info object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#info-object) | Yes | N/A
`externalDocs(block)` | [See External Documentation object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#external-documentation-object) | No | null

#### info block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`title` |   The title of the API. | Yes | N/A
`description` | A short description of the API | No | null
`termsOfService` |  URL to the Terms of Service for the API | No | null
`version` | The version of the OpenAPI document | Yes | N/A
`contact(block)` | [See Contact object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#contact-object) | No | null
`license(block)` | [See License object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#licenseObject) | No | null

#### contact block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`name` |  The identifying name of the contact person/organization. | No | null
`url` | The URL pointing to the contact information. MUST be in the format of a URL. | No | null
`email` | The email address of the contact person/organization. MUST be in the format of an email address. | No | null

#### license block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`name` | The license name used for the API. | Yes | N/A
`url` | A URL to the license used for the API. MUST be in the format of a URL. | No | null

#### externalDocs block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`description` | A short description of the target documentation. | No | null
`url` | The URL for the target documentation. MUST be in the format of a URL. | Yes | N/A


#### servers block
A list of server blocks

#### server block
Parameter | Description | Required | Default
--------- | ----------- | -------- | -------
`description` | A short description of the server. | No | null
`url` | The URL for the server. MUST be in the format of a URL. | Yes | N/A


# Building the plugin

TODO