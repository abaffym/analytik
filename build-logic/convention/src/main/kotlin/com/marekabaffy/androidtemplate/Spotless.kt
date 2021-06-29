package com.marekabaffy.androidtemplate

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project

internal fun Project.configureSpotless(
    extension: SpotlessExtension,
) {
    extension.apply {
        kotlin {
            target("src/**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint()
        }

        kotlinGradle {
            target("*.gradle.kts")
            targetExclude("**/build/**/*.kts")
            ktlint()
        }

        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }
}
