plugins {
    `java-gradle-plugin`
    alias(libs.plugins.buildLogic.kotlin.library)
    alias(libs.plugins.maven.publish)
}

gradlePlugin {
    plugins {
        create("analytik") {
            id = "com.marekabaffy.analytik"
            implementationClass = "com.marekabaffy.analytik.gradle.AnalytikPlugin"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(projects.analytikCore)
    implementation(projects.analytikGenerator)
    implementation(projects.analytikEncoder)

    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.android.gradlePlugin)
    implementation(libs.android.tools.sdkcommon)

    testImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.engine)
    testImplementation(gradleTestKit())
}
