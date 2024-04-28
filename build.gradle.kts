import com.booruchan.buildsrc.plugin.BooruchanDeployAndroidPlugin

plugins {
    kotlin("jvm") version "1.7.20"
}

allprojects {
    group = "org.booruchan.extensions"
}

// Plugin for preparing apks for publishing
// Responsible for collecting apks, aligning and signing,
// Creating json metadata about whole bunch of apks
apply<BooruchanDeployAndroidPlugin>()
