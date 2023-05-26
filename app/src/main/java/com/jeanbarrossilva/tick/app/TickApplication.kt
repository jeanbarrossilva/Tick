package com.jeanbarrossilva.tick.app

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.jeanbarrossilva.tick.std.launchable.Launchable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal open class TickApplication : Application(), Launchable {
    private val preferences
        get() = getSharedPreferences("tick", Context.MODE_PRIVATE)

    override fun onCreate() {
        super.onCreate()
        markAsLaunched()
        inject()
    }

    override fun count(): Int {
        return preferences.getInt(LAUNCH_COUNT_PREFERENCE_KEY, 0)
    }

    override fun markAsLaunched() {
        preferences.edit {
            putInt(LAUNCH_COUNT_PREFERENCE_KEY, count() + 1)
        }
    }

    private fun inject() {
        startKoin {
            androidContext(this@TickApplication)
            modules(TickModule())
        }
    }

    companion object {
        private const val LAUNCH_COUNT_PREFERENCE_KEY = "launch_count"
    }
}
