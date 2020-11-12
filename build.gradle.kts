plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.72" apply false
}

val localRepository: String by project.extra

allprojects {
    apply(plugin = "maven-publish")
    group = "com.rameshkp"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    publishing {
        repositories {
            maven {
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