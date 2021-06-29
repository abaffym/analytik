import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.marekabaffy.analytik.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinLibrary") {
            id = "analytik.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("mavenPublish") {
            id = "analytik.maven.publish"
            implementationClass = "MavenPublishConventionPlugin"
        }
    }
}
