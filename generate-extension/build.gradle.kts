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

    implementation("com.samskivert:jmustache:${Version.jmustache}")
    implementation("io.insert-koin:koin-core:${Version.koin.core}")

    implementation("org.slf4j:slf4j-api:${Version.slf4j.api}")
    implementation("org.slf4j:slf4j-simple:${Version.slf4j.simple}")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

val extensions = listOf("Lolibooru")
val types = listOf("runAssembleDebug")

var runTaskArgumentsString: String by extra {
    properties.getOrDefault("RunTaskArgumentsString", "") as String
}

tasks {
    extensions.forEach { extension ->
        types.forEach { type ->
            register("$type$extension") {
                runTaskArgumentsString = "$type $extension"
                dependsOn(named("run"))
            }
        }
    }

    named<JavaExec>("run") {
        setArgsString(runTaskArgumentsString)
    }
}
