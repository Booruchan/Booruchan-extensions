plugins {
    `java-library`
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
    id("maven-publish")
}

version = "0.3.5"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

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
            artifactId = name

            // Version Library Name (Example : "1.0.0")
            version = version.toString()

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}
