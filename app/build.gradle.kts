import com.jeanbarrossilva.tick.Dependencies
import com.jeanbarrossilva.tick.Dimensions
import com.jeanbarrossilva.tick.Versions

plugins {
    id("com.android.application")

    @Suppress("RemoveRedundantQualifierName")
    id("com.google.devtools.ksp") version com.jeanbarrossilva.tick.Versions.KSP

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

    @Suppress("UnstableApiUsage")
    flavorDimensions += Dimensions.VERSION

    productFlavors {
        create("default") {
            dimension = Dimensions.VERSION
        }

        create("demo") {
            dimension = Dimensions.VERSION
            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
        }
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
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
}

dependencies {
    implementation(project(":core:room"))
    implementation(project(":feature:composer:group"))
    implementation(project(":feature:composer:todo"))
    implementation(project(":feature:to-dos"))
    implementation(project(":platform:launchable"))
    implementation(project(":platform:theme"))
    implementation(project(":std:loadable"))
    implementation(project(":std:selectable"))
    implementation(Dependencies.KOIN)
    implementation(Dependencies.WORK)

    @Suppress("SpellCheckingInspection")
    implementation(
        "io.github.raamcosta.compose-destinations:animations-core:${Versions.COMPOSE_DESTINATIONS}"
    )

    @Suppress("SpellCheckingInspection")
    implementation("io.github.raamcosta.compose-destinations:core:${Versions.COMPOSE_DESTINATIONS}")

    @Suppress("SpellCheckingInspection")
    ksp("io.github.raamcosta.compose-destinations:ksp:${Versions.COMPOSE_DESTINATIONS}")
}
