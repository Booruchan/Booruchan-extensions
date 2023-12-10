plugins {
    id("java")
    id("application")
}

application {
    mainClass = "org.booruchan.extensions.generate.MainKt"
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

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


var runTaskArgumentsString: String by extra {
    properties.getOrDefault("RunTaskArgumentsString", "") as String
}

tasks {
    val runAssembleDebug by registering {
        runTaskArgumentsString = "assembleDebug"
        dependsOn(named("run"))
    }
}

tasks.named<JavaExec>("run") {
    setArgsString(runTaskArgumentsString)
}

