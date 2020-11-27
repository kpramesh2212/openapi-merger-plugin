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
            maven {
                name = "central"
                gradle.taskGraph.whenReady {
                    url = uri (
                            when {
                                hasTask(":release") -> {
                                    val mavenCentralStagingUrl: String by project.extra
                                    mavenCentralStagingUrl
                                }
                                project.hasProperty("publishToCentral") && project.version.toString().contains("SNAPSHOT") -> {
                                    val mavenCentralSnapshotUrl: String by project.extra
                                    mavenCentralSnapshotUrl
                                }
                                else -> "$buildDir/localRepo"
                            }
                    )
                    if (hasTask(":release") || project.hasProperty("publishToCentral")) {
                        val mavenCentralUsername: String by project.extra
                        val mavenCentralPassword: String by project.extra
                        credentials {
                            username = mavenCentralUsername
                            password = mavenCentralPassword
                        }
                    }
                }
            }
            maven {
                name = "local"
                url = uri(localRepository)
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

gradle.taskGraph.whenReady {
    val publishGradlePluginToPortal = project(":openapi-merger-gradle-plugin").tasks.named("publishPlugins")
    if (hasTask(":release")) {
        val release by tasks.existing {
            dependsOn(build)
            dependsOn(publishGradlePluginToPortal)
        }
    }
}

val clean by tasks.registering(Delete::class) {
    delete = setOf(buildDir)
}

release {
    (getProperty("git") as net.researchgate.release.GitAdapter.GitConfig).apply {
        requireBranch = "main"
    }
}