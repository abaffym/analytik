plugins {
    alias(libs.plugins.buildLogic.kotlin.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kotlin.serialization)
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(projects.analytikCore)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization)

    testImplementation(libs.kotlin.test)
}
