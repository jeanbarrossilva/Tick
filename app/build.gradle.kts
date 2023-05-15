import com.jeanbarrossilva.tick.Versions

plugins {
    id("com.android.application")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("kotlin-android")
}

android {
    namespace = "com.jeanbarrossilva.tick.app"
    compileSdk = Versions.Tick.TARGET_SDK

    defaultConfig {
        applicationId = "com.jeanbarrossilva.tick"
        minSdk = Versions.Tick.MIN_SDK
        targetSdk = Versions.Tick.TARGET_SDK
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    @Suppress("SpellCheckingInspection")
    ksp("io.github.raamcosta.compose-destinations:ksp:${Versions.COMPOSE_DESTINATIONS}")

    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.jeanbarrossilva.loadable:loadable:1.4.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.2-alpha")

    @Suppress("SpellCheckingInspection")
    implementation("io.github.raamcosta.compose-destinations:core:${Versions.COMPOSE_DESTINATIONS}")

    implementation("io.insert-koin:koin-androidx-compose:3.4.4")

    @Suppress("SpellCheckingInspection")
    implementation("org.ocpsoft.prettytime:prettytime:5.0.4.Final")
}
