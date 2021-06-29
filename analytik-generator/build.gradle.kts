plugins {
    alias(libs.plugins.buildLogic.kotlin.library)
    alias(libs.plugins.maven.publish)
}

dependencies {
    implementation(projects.analytikCore)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinpoet)
}
