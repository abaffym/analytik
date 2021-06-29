package com.marekabaffy.analytik.gradle.kotlin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.marekabaffy.analytik.gradle.GenerateAnalyticsTask
import org.gradle.api.DomainObjectSet
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.tasks.TaskProvider
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

private val Project.findAndroidBaseExtension: BaseExtension?
    get() = extensions.findByType(BaseExtension::class.java)

private val Project.getKotlinExtension: KotlinProjectExtension
    get() = extensions.getByType(KotlinProjectExtension::class.java)

internal fun Project.sources(): List<Source> {
    val androidBaseExtension = findAndroidBaseExtension
    if (androidBaseExtension != null) return androidBaseExtension.sources(this)

    return getKotlinExtension.sources(project)
}

private fun KotlinProjectExtension.sources(project: Project): List<Source> {
    return listOf(
        Source(
            type = KotlinPlatformType.jvm,
            name = "main",
            sourceSets = listOf("main"),
            sourceDirectorySet = sourceSets.getByName("main").kotlin,
            registerTaskDependency = { task ->
                project.tasks.named("compileKotlin").configure { it.dependsOn(task) }
            },
        ),
    )
}

private fun BaseExtension.sources(project: Project): List<Source> {
    val variants: DomainObjectSet<out BaseVariant> =
        when (this) {
            is AppExtension -> applicationVariants
            is LibraryExtension -> libraryVariants
            else -> throw IllegalStateException("Unknown Android plugin $this")
        }

    val kotlinSourceSets = project.getKotlinExtension.sourceSets
    val sourceSets =
        sourceSets.associate { sourceSet ->
            sourceSet.name to kotlinSourceSets.getByName(sourceSet.name).kotlin
        }

    return variants.map { variant ->
        Source(
            type = KotlinPlatformType.androidJvm,
            name = variant.name,
            variantName = variant.name,
            sourceDirectorySet =
                sourceSets[variant.name]
                    ?: throw IllegalStateException("Couldn't find ${variant.name} in $sourceSets"),
            sourceSets = variant.sourceSets.map { it.name },
            registerTaskDependency = { task ->
                variant.registerJavaGeneratingTask(task, task.get().outputDirectory)
                project.tasks.named("compile${variant.name.capitalized()}Kotlin").dependsOn(task)
            },
        )
    }
}

internal data class Source(
    val type: KotlinPlatformType,
    val name: String,
    val variantName: String? = null,
    val sourceDirectorySet: SourceDirectorySet,
    val sourceSets: List<String>,
    val registerTaskDependency: (TaskProvider<GenerateAnalyticsTask>) -> Unit,
)
