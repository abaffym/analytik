rootProject.name = "analytik"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    "analytik-core",
    "analytik-encoder",
    "analytik-generator",
    "analytik-gradle-plugin",
//    "analytik-ui-desktop",
    "sample:android",
    "sample:kotlin-jvm"
)
