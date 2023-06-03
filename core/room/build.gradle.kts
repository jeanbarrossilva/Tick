
import com.jeanbarrossilva.tick.Dependencies
import com.jeanbarrossilva.tick.Versions

plugins {
    id("com.android.library")

    @Suppress("RemoveRedundantQualifierName")
    id("com.google.devtools.ksp") version com.jeanbarrossilva.tick.Versions.KSP

    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jeanbarrossilva.tick.core.room"
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

dependencies {
    androidTestImplementation(Dependencies.COROUTINES_TEST)
    androidTestImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.TURBINE)
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")

    api(project(":core"))
    api("androidx.room:room-runtime:${Versions.ROOM}")

    implementation(project(":platform:launchable"))
    implementation(Dependencies.KOIN)
    implementation("androidx.room:room-ktx:${Versions.ROOM}")

    ksp("androidx.room:room-compiler:${Versions.ROOM}")

    testImplementation(Dependencies.JUNIT)
}
