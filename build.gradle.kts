import com.booruchan.buildsrc.plugin.BooruchanDeployAndroidPlugin

buildscript {
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

plugins {
    kotlin("jvm") version "1.7.20"
    `maven-publish`
}

allprojects {
    group = "org.booruchan.extensions"
}

// Plugin for preparing apks for publishing
// Responsible for collecting apks, aligning and signing,
// Creating json metadata about whole bunch of apks
apply<BooruchanDeployAndroidPlugin>()