package com.marekabaffy.analytik.gradle

import com.marekabaffy.analytik.AnalytikGenerator
import com.marekabaffy.analytik.encoder.json.JsonAnalytikEncoder
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

open class GenerateAnalyticsTask : DefaultTask() {
    @InputFile
    lateinit var inputFile: File

    @Input
    lateinit var className: String

    @Input
    lateinit var packageName: String

    @OutputDirectory
    lateinit var outputDirectory: File

    @TaskAction
    fun execute() {
        val parser = JsonAnalytikEncoder()
        val generator = AnalytikGenerator()

        val file = File(inputFile.path)
        val data = parser.decode(file.readText())

        generator.generate(
            data = data,
            path = outputDirectory.path,
            className = className,
            packageName = packageName,
        )
    }
}
