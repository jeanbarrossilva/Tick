package com.jeanbarrossilva.tick

object Dependencies {
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    const val JUNIT = "junit:junit:4.13.2"
    const val KOIN = "io.insert-koin:koin-androidx-compose:3.4.4"
    const val LOADABLE = "com.jeanbarrossilva.loadable:loadable:1.4.1"
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    const val TURBINE = "app.cash.turbine:turbine:0.13.0"
    const val WORK = "androidx.work:work-runtime-ktx:2.8.1"
}
