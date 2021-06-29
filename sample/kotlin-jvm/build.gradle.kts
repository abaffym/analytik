plugins {
    alias(libs.plugins.buildLogic.kotlin.library)
    id("com.marekabaffy.analytik") version "0.0.1-SNAPSHOT"
}

dependencies {
    implementation(libs.kotlin.stdlib)
}

analytik {
    className = "KotlinJvmDemoAnalyticsEvent"
    packageName = "com.marekabaffy.analytik.sample.kotlinjvm"
    filePath = "${projectDir.path}/src/main/analytik/events.json"
}
