package com.jeanbarrossilva.tick

import org.gradle.api.JavaVersion

object Versions {
    const val COMPOSE_DESTINATIONS = "1.8.41-beta"

    val java = JavaVersion.VERSION_17

    object Tick {
        const val MIN_SDK = 26
        const val TARGET_SDK = 33
    }
}
