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
    api(project(":core-test"))
    api(project(":core:in-memory"))

    testImplementation(kotlin("test"))
}
