package com.jeanbarrossilva.tick

import org.gradle.api.JavaVersion

object Versions {
    const val COMPOSE_COMPILER = "1.4.7"
    const val COMPOSE_DESTINATIONS = "1.9.42-beta"
    const val COROUTINES = "1.7.1"
    const val KSP = "1.8.21-1.0.11"
    const val LOADABLE = "1.5.0"
    const val ROOM = "2.5.1"

    val java = JavaVersion.VERSION_17

    object Tick {
        const val MIN_SDK = 26
        const val TARGET_SDK = 33
    }
}
