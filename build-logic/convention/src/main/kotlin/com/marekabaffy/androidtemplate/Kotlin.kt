package com.marekabaffy.androidtemplate

import com.marekabaffy.androidtemplate.extensions.kotlinOptions
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.get

internal fun Project.configureKotlin(
    javaPluginExtension: JavaPluginExtension,
) = with(javaPluginExtension) {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    sourceSets["main"].java.srcDir("src/main/kotlin")

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
