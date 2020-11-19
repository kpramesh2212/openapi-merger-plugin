plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.72" apply false
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
            if (project.hasProperty("publishToLocal")) {
                val localRepository: String by project.extra
                maven {
                    url = uri(localRepository)
                }
            }
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