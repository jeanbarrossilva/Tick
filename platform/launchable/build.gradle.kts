
import com.jeanbarrossilva.tick.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    @Suppress("SpellCheckingInspection")
    namespace = "com.jeanbarrossilva.platform.launchable"

    compileSdk = Versions.Tick.TARGET_SDK

    defaultConfig {
        minSdk = Versions.Tick.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true

            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    kotlinOptions {
        jvmTarget = Versions.java.toString()
    }
}
