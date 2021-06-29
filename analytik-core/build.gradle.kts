plugins {
    alias(libs.plugins.buildLogic.kotlin.library)
    alias(libs.plugins.maven.publish)
}

dependencies {
    implementation(libs.kotlin.stdlib)
}
