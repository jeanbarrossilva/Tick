import com.jeanbarrossilva.tick.Dependencies
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
    api(project(":core"))
    api(Dependencies.JUNIT)

    implementation(kotlin("test"))
    implementation(Dependencies.COROUTINES_TEST)
    implementation(Dependencies.TURBINE)
}
