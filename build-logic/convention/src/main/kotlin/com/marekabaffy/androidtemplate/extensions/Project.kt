package com.marekabaffy.androidtemplate.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions(block)
    }
}
