package com.marekabaffy.androidtemplate

import com.marekabaffy.androidtemplate.extensions.libs
import com.marekabaffy.androidtemplate.extensions.version
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension

fun Project.configurePublishing(
    publishingExtension: PublishingExtension
) = with(publishingExtension) {
    repositories {
        maven {
            name = "installLocally"
            url = uri("${rootProject.layout.buildDirectory}/localMaven")
        }

        maven {
            val releasesRepoUrl =
                "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl =
                "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(
                if (libs.version("analytik").endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            )

            credentials {
                username = properties["ossrhUsername"].toString()
                password = properties["ossrhPassword"].toString()
            }
        }
    }
}
