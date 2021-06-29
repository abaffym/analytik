package com.marekabaffy.analytik.gradle

import com.android.build.gradle.api.AndroidBasePlugin
import com.marekabaffy.analytik.gradle.kotlin.sources
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin

@Suppress("unused")
class AnalytikPlugin : Plugin<Project> {
    companion object {
        const val ANALYTIK_TASK_NAME = "generateAnalytics"
        const val ANALYTIK_GROUP = "AnalytiK"
        const val ANALYTIK_EXTENSION = "analytik"

        private const val DEFAULT_FILE_INPUT = "events.json"
        private const val DEFAULT_CLASS_NAME = "AnalyticsEvent"
    }

    private val android = AtomicBoolean(false)
    private val kotlin = AtomicBoolean(false)

    override fun apply(project: Project) {
        val extension = project.extensions.create(ANALYTIK_EXTENSION, AnalytikExtension::class.java)

        project.plugins.withType(AndroidBasePlugin::class.java) {
            android.set(true)
            project.afterEvaluate {
                project.setupAnalytikTasks(afterAndroid = true, extension)
            }
        }

        project.plugins.withType(KotlinBasePlugin::class.java) {
            kotlin.set(true)
        }

        project.afterEvaluate {
            project.setupAnalytikTasks(afterAndroid = false, extension)
        }
    }

    private fun Project.setupAnalytikTasks(
        afterAndroid: Boolean,
        extension: AnalytikExtension,
    ) {
        if (android.get() && !afterAndroid) return

        check(kotlin.get()) {
            "Analytik Gradle plugin applied in " +
                "project '${project.path}' but no supported Kotlin plugin was found"
        }

        val generatedSourceDirectory =
            File(
                project.layout.buildDirectory.asFile.get(),
                "generated/analytik/AnalytikEvents",
            )

        val sources = sources()

        val task =
            project.tasks.register(
                // name =
                ANALYTIK_TASK_NAME,
                // type =
                GenerateAnalyticsTask::class.java,
            ) { task ->
                task.group = ANALYTIK_GROUP
                task.description = "Generates analytics events"
                task.className = extension.className.getOrElse(DEFAULT_CLASS_NAME)
                task.packageName = extension.packageName.getOrElse("")
                task.outputDirectory = generatedSourceDirectory
                task.inputFile =
                    File(
                        extension.filePath.getOrElse(
                            "${project.rootDir.path}/analytik/$DEFAULT_FILE_INPUT",
                        ),
                    )
            }

        sources.forEach { source ->
            source.sourceDirectorySet.srcDir(generatedSourceDirectory.toRelativeString(project.projectDir))
            source.registerTaskDependency(task)
        }
    }
}
