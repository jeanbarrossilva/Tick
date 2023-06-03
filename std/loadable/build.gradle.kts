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
    api(Dependencies.LOADABLE)

    implementation(Dependencies.COROUTINES_CORE)

    testImplementation(kotlin("test"))
    testImplementation(Dependencies.COROUTINES_TEST)
    testImplementation(Dependencies.TURBINE)
}
