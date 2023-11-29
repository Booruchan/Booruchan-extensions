buildscript {
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

plugins {
    kotlin("jvm") version "1.9.21"
}

allprojects {
    group = "org.booruchan.extensions"
}


subprojects {
    val subprojectVersion = this.version.toString()
    val subprojectArtifact = this.name

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        publications {
            register<MavenPublication>("maven") {
                // Library Package Name
                // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
                groupId = "org.booruchan.extensions"

                // Library Name / Module Name
                // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
                artifactId = subprojectArtifact

                // Version Library Name (Example : "1.0.0")
                version = subprojectVersion

                afterEvaluate {
                    from(components["java"])
                }
            }
        }
    }
}