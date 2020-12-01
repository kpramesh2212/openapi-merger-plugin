rootProject.name = "openapi-merger-plugin"

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}

include("openapi-merger-gradle-plugin")
include("openapi-merger-maven-plugin")
include("openapi-merger-app")