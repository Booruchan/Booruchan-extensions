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

// Configure the publication to Maven repository
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            // Configure the coordinates and other settings here
            groupId = "com.booruchan"
            artifactId = "sdk"
            version = "0.3.5"

//            // Optionally, you can attach sources and javadoc
//            val sourcesJar = tasks.create("sourceJar", Jar::class) {
//                archiveClassifier.set("sources")
//                from(sourceSets["main"].allSource)
//            }
//            val javadocJar = tasks.create("javadocJar", Javadoc::class) {
//                dependsOn("javadoc")
//                from(tasks["javadoc"].outputs)
//            }
//            artifacts {
//                add(sourcesJar)
//                add(javadocJar)
//            }
        }
    }
}
