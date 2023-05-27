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
}
