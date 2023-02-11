# **Introduction to openapi-merger-maven-plugin**

Open API Merger Maven Plugin - Merges multiple OpenAPI-3 specification file into a single file.

This Maven plugin provides the capability to merge multiple open api v3 specification file into a single file from a Maven build. 

Compatibility Notes
-------------------

The plugin is build on maven version 3.6.3

The Java version used to compile the plugin 1.8 

How To Use
----------

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.rameshkp</groupId>
            <artifactId>openapi-merger-maven-plugin</artifactId>
            <version>1.0.5</version>
        </plugin>
    </plugins>
</build>
```

Note: For latest versions of the plugins please check the maven central portal

What does this plugin do
-----------------------

[open api merger plugin readme](https://github.com/kpramesh2212/openapi-merger-plugin/blob/main/README.md)

How the plugin works
------------

Add this plugin to the pom.xml as shown above and then run

```bash
mvn openapi-merger:merge
``` 

Using configuration block users can perform customization as explained below

Customization
-------------

The following customizations can be done using the configuration block as follows

```xml
<configuration>
    <inputDir>open-api-files</inputDir>
    <outputDir>target/my-custom-dir</outputDir>
    <outputFileFormat>JSON</outputFileFormat>
    <outputFileName>open</outputFileName>
    <openApi>
        <version>3.0.3</version>
        <info>
            <version>1.0.0-SNAPSHOT</version>
            <title>Merged Title</title>
            <description>A short description of the title</description>
            <termsOfService>"https://openapi-merger.com/terms-of-service"</termsOfService>
            <contact>
                <name>ContactName</name>
                <email>contact@openapi.merger.com</email>
                <url>https://openapimerger.com</url>
            </contact>
            <license>
                <name>Apache License v2.0</name>
                <url>https://apache.org/v2</url>
            </license>
        </info>
        <externalDocs>
            <description>External Docs</description>
            <url>https://external-docs.com/url</url>
        </externalDocs>
    </openApi>
</configuration>
```
#### openApiMerger block
| Parameter          | Description                                                                                                                            | Required | Default          |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------|----------|------------------|
| `inputDir`         | The input directory containing the openapi v3 specification files. The input directory can contain files in both yaml and json format. | Yes      | N/A              |
| `outputDir`        | The output directory to place the merged open api v3 specification file.                                                               | No       | target/open-api/ |
| `outputFileName`   | Name of the merged output openapi v3 specification file                                                                                | No       | openapi          |
| `outputFileFormat` | The format of the output. Can either be one of YAML or JSON                                                                            | No       | YAML             |
| `openApi(Block)`   | The openApi block is used to customize the common openApi object for the merged file.                                                  | Yes      | N/A              |

#### openApi block
| Parameter             | Description                                                                                                                                                                                | Required | Default |
|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|---------|
| `version`             | The version of open api.                                                                                                                                                                   | Yes      | N/A     |
| `info(block)`         | [See Info object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#info-object)                                     | Yes      | N/A     |
| `externalDocs(block)` | [See External Documentation object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#external-documentation-object) | No       | null    |

#### info block
| Parameter        | Description                                                                                                                                                  | Required | Default |
|------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|---------|
| `title`          | The title of the API.                                                                                                                                        | Yes      | N/A     |
| `description`    | A short description of the API                                                                                                                               | No       | null    |
| `termsOfService` | URL to the Terms of Service for the API                                                                                                                      | No       | null    |
| `version`        | The version of the OpenAPI document                                                                                                                          | Yes      | N/A     |
| `contact(block)` | [See Contact object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#contact-object) | No       | null    |
| `license(block)` | [See License object in open api v3 specification for explanation](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#licenseObject)  | No       | null    |

#### contact block
| Parameter | Description                                                                                      | Required | Default |
|-----------|--------------------------------------------------------------------------------------------------|----------|---------|
| `name`    | The identifying name of the contact person/organization.                                         | No       | null    |
| `url`     | The URL pointing to the contact information. MUST be in the format of a URL.                     | No       | null    |
| `email`   | The email address of the contact person/organization. MUST be in the format of an email address. | No       | null    |

#### license block
| Parameter | Description                                                            | Required | Default |
|-----------|------------------------------------------------------------------------|----------|---------|
| `name`    | The license name used for the API.                                     | Yes      | N/A     |
| `url`     | A URL to the license used for the API. MUST be in the format of a URL. | No       | null    |

#### externalDocs block
| Parameter     | Description                                                           | Required | Default |
|---------------|-----------------------------------------------------------------------|----------|---------|
| `description` | A short description of the target documentation.                      | No       | null    |
| `url`         | The URL for the target documentation. MUST be in the format of a URL. | Yes      | N/A     |

# Building the plugin

TODO