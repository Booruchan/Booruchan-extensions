plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
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
afterEvaluate {
    // Configure the publication to Maven repository
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.booruchan"
                artifactId = "sdk"
                version = "0.3.5"
                from(components["java"])
            }
        }
    }
}
