import com.jeanbarrossilva.tick.Dependencies
import com.jeanbarrossilva.tick.Versions

plugins {
    id("java-library")
    kotlin("jvm")
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java
}

dependencies {
    api(project(":core"))

    testImplementation(project(":core:in-memory-test"))
    testImplementation(Dependencies.COROUTINES_TEST)
    testImplementation(Dependencies.TURBINE)
}
