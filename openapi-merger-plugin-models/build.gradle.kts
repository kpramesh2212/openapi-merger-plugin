plugins {
    kotlin("jvm")
    `maven-publish`
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
        create<MavenPublication>("openapiMergerModel") {
            from(components["java"])
        }
    }
}