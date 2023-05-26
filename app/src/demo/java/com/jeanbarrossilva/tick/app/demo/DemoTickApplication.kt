package com.jeanbarrossilva.tick.app.demo

import com.jeanbarrossilva.tick.app.TickApplication
import com.jeanbarrossilva.tick.app.demo.extensions.addGroups
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.std.launchable.isFirstLaunch
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class DemoTickApplication : TickApplication() {
    override fun onCreate() {
        super.onCreate()
        MainScope().launch { addSamplesOnFirstLaunch() }
    }

    private suspend fun addSamplesOnFirstLaunch() {
        if (isFirstLaunch) {
            addSamples()
        }
    }

    private suspend fun addSamples() {
        val editor by inject<ToDoEditor>()
        editor.addGroups(ToDoGroup.samples)
    }

    companion object {
    }
}
