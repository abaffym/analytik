import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.buildLogic.kotlin.library)
    alias(libs.plugins.compose)
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "analytik-desktop"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(projects.analytikCore)
    implementation(projects.analytikEncoder)

    implementation(libs.mpfilepicker)
}
