plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    `maven-publish`
    id("io.kotest") version "0.2.6"
}

dependencies {
    implementation(kotlin(module = "stdlib"))
    testImplementation(group = "io.kotest", name = "kotest-runner-junit5", version = "4.1.2")
    testImplementation(group = "io.kotest", name = "kotest-assertions-core", version = "4.1.2")
    testImplementation(group = "io.kotest", name = "kotest-property", version = "4.1.2")
    implementation(project(":openapi-merger-app"))
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

tasks.withType<Test> {
    useJUnitPlatform()
}
