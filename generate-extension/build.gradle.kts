plugins {
    id("java")
}

group = "org.booruchan.extensions"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.samskivert:jmustache:1.15")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}