package com.jeanbarrossilva.tick.app

import android.app.Application
import com.jeanbarrossilva.tick.feature.todos.ToDosModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class TickApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() {
        startKoin {
            androidContext(this@TickApplication)
            modules(ToDosModule())
        }
    }
}
