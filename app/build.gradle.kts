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
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    implementation(project(":core:room"))
    implementation(project(":platform:launchable"))
    implementation(project(":platform:theme"))
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.jeanbarrossilva.loadable:loadable:1.4.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.2-alpha")

    @Suppress("SpellCheckingInspection")
    implementation(
        "io.github.raamcosta.compose-destinations:animations-core:${Versions.COMPOSE_DESTINATIONS}"
    )

    @Suppress("SpellCheckingInspection")
    implementation("io.github.raamcosta.compose-destinations:core:${Versions.COMPOSE_DESTINATIONS}")

    implementation(Dependencies.KOIN)

    @Suppress("SpellCheckingInspection")
    implementation("org.ocpsoft.prettytime:prettytime:5.0.4.Final")

    implementation("androidx.work:work-runtime-ktx:2.8.1")

    @Suppress("SpellCheckingInspection")
    ksp("io.github.raamcosta.compose-destinations:ksp:${Versions.COMPOSE_DESTINATIONS}")

    testImplementation(Dependencies.JUNIT)
}
