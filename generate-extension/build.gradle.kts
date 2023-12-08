plugins {
    id("java")
}

group = "org.booruchan.extensions"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":extension-sdk"))
    implementation(project(":extension-lolibooru"))

    implementation("com.samskivert:jmustache:1.15")

    implementation("io.insert-koin:koin-core:3.4.3")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}