plugins {
    kotlin("jvm")
    `maven-publish`
    signing
}

dependencies {
    implementation(kotlin(module = "stdlib"))
    implementation(group = "io.swagger.parser.v3", name = "swagger-parser", version = "2.0.23")
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")

    implementation(group = "javax.validation", name = "validation-api", version = "2.0.0.Final")
    runtimeOnly(group = "org.hibernate.validator", name = "hibernate-validator", version = "6.0.2.Final")
    runtimeOnly(group = "org.hibernate.validator", name = "hibernate-validator-annotation-processor", version = "6.0.2.Final")
    runtimeOnly(group = "javax.el", name = "javax.el-api", version = "3.0.0")
    runtimeOnly(group = "org.glassfish.web", name = "javax.el", version = "2.2.6")
}

publishing {
    publications {
        create<MavenPublication>("openApiMergerApp") {
            from(components["java"])

            pom {
                name.set("Open API V3 Merger Application")
                description.set("A Kotlin app to merge open api v3 specification files")
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
    }
}

signing {
    sign(publishing.publications["openApiMergerApp"])
}