plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.72" apply false
    id("net.researchgate.release") version "2.8.1"
}

val localRepository = "$buildDir/openapi-repo"

allprojects {
    apply(plugin = "maven-publish")

    project.extra["localRepository"] = localRepository

    repositories {
        mavenCentral()
    }

    publishing {
        repositories {
            if (project.hasProperty("publishToCentral")) {
                val mavenCentralUsername: String by project.extra
                val mavenCentralPassword: String by project.extra
                val mavenCentralStagingUrl: String by project.extra
                val mavenCentralSnapshotUrl: String by project.extra
                maven {
                    url = uri(if ("${project.version}".contains("SNAPSHOT")) mavenCentralSnapshotUrl else mavenCentralStagingUrl)
                    credentials {
                        username = mavenCentralUsername
                        password = mavenCentralPassword
                    }
                }
            } else {
                val localRepository: String by project.extra
                maven {
                    url = uri(localRepository)
                }
            }
        }
    }
}

subprojects {
    apply(plugin = "java")
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


val publishMergerApp = project(":openapi-merger-app").tasks.named("publish")
val publishGradlePlugin = project(":openapi-merger-gradle-plugin").tasks.named("publish")
val publishMavenPlugin = project(":openapi-merger-maven-plugin").tasks.named("publish")
val build by tasks.registering {
    dependsOn(publishMergerApp)
    dependsOn(publishGradlePlugin)
    dependsOn(publishMavenPlugin)
}