import com.jeanbarrossilva.tick.Versions

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}
