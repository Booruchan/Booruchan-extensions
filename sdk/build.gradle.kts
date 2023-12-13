plugins {
    `java-library`
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
}

version = "0.3.4"

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
