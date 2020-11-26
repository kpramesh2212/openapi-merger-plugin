plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    `maven-publish`
    id("io.kotest") version "0.2.6"
    id("com.gradle.plugin-publish") version "0.12.0"
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.version != "1.3.72") {
            useVersion("1.3.72")
            because("Gradle 6.7 uses version 1.3.72")
        }
    }
}

dependencies {
    implementation(kotlin(module = "stdlib"))
    implementation(project(":openapi-merger-app"))

    testImplementation(group = "io.kotest", name = "kotest-assertions-core-jvm", version = "4.3.1")
    testImplementation(group = "io.kotest", name = "kotest-framework-engine-jvm", version = "4.3.1")
    testImplementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.4.1")
}

pluginBundle {
    website = "https://github.com/kpramesh2212/openapi-merger-plugin"
    vcsUrl = "https://github.com/kpramesh2212/openapi-merger-plugin.git"
    tags = listOf("openapi-3.0", "merger")
}

gradlePlugin {
    plugins {
        create("openapi-merger-gradle-plugin") {
            id = "com.rameshkp.openapi-merger-gradle-plugin"
            displayName = "OpenAPI 3 merger gradle plugin"
            description = "A gradle plugin to merge multiple openapi files"
            implementationClass = "com.rameshkp.openapi.merger.gradle.plugin.OpenApiMergerGradlePlugin"
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
