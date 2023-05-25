package com.jeanbarrossilva.tick.app.demo

import android.content.Context
import androidx.core.content.edit
import com.jeanbarrossilva.tick.app.TickApplication
import com.jeanbarrossilva.tick.app.demo.extensions.addGroups
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class DemoTickApplication : TickApplication() {
    override fun onCreate() {
        super.onCreate()
        MainScope().launch { addSamplesOnFirstLaunch() }
    }

    private suspend fun addSamplesOnFirstLaunch() {
        val preferences = getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val isFirstLaunch = preferences.getBoolean(FIRST_LAUNCH_PREFERENCE_KEY, true)
        if (isFirstLaunch) {
            preferences.edit { putBoolean(FIRST_LAUNCH_PREFERENCE_KEY, false) }
            addSamples()
        }
    }

    private suspend fun addSamples() {
        val editor by inject<ToDoEditor>()
        editor.addGroups(ToDoGroup.samples)
    }

    companion object {
        private const val PREFERENCES_FILE_NAME = "tick"
        private const val FIRST_LAUNCH_PREFERENCE_KEY = "is_first_launch"
    }
}
