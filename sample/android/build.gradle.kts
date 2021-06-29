plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.marekabaffy.analytik") version "0.0.1-SNAPSHOT"
}

android {
    namespace = "com.marekabaffy.analytik.sample.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.marekabaffy.analytik.sample.android"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

analytik {
    className = "AndroidDemoAnalyticsEvent"
    packageName = "com.marekabaffy.analytik.sample.android"
    filePath = "${projectDir.path}/src/main/analytik/events.json"
}

dependencies {
}
