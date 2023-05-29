import com.jeanbarrossilva.tick.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jeanbarrossilva.tick.feature.todos"
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

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    kotlinOptions {
        jvmTarget = Versions.java.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":platform:theme"))
    implementation(project(":std:loadable"))

    @Suppress("SpellCheckingInspection")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.2-alpha")

    @Suppress("SpellCheckingInspection")
    implementation("org.ocpsoft.prettytime:prettytime:5.0.4.Final")
}
