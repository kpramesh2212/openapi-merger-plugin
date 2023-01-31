plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    `maven-publish`
    id("io.kotest")
    id("com.gradle.plugin-publish") version "0.12.0"
    id("org.jetbrains.dokka")
    signing
}

java {
    withJavadocJar()
    withSourcesJar()
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
val dokkaJavadoc by tasks.existing
val javadocJar by tasks.existing(Jar::class) {
    dependsOn(dokkaJavadoc)
    from("$buildDir/dokka/javadoc")
}
project.afterEvaluate {
    val publishPluginJavaDocsJar by tasks.existing(Jar::class) {
        dependsOn(dokkaJavadoc)
        from("$buildDir/dokka/javadoc")
    }
}

afterEvaluate {
    signing {
        sign(publishing.publications["pluginMaven"])
        sign(publishing.publications["openapi-merger-gradle-pluginPluginMarkerMaven"])
    }

    publishing {
        publications {
            named<MavenPublication>("pluginMaven") {
               setPomDetails(this)
            }
            named<MavenPublication>("openapi-merger-gradle-pluginPluginMarkerMaven") {
                setPomDetails(this)
            }
        }
    }
}

fun setPomDetails(mavenPublication: MavenPublication) {
     mavenPublication.pom {
        name.set("Open API V3 Merger gradle plugin")
        description.set("A gradle plugin to merge open api v3 specification files")
        url.set("https://github.com/kpramesh2212/openapi-merger-plugin")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("rameshkp")
                name.set("Ramesh KP")
                email.set("kpramesh2212@gmail.com")
            }
        }
        scm {
            connection.set("git@github.com:kpramesh2212/openapi-merger-plugin.git")
            developerConnection.set("git@github.com:kpramesh2212/openapi-merger-plugin.git")
            url.set("https://github.com/kpramesh2212/openapi-merger-plugin")
        }
    }
}