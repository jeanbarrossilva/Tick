package com.jeanbarrossilva.tick.app.demo

import com.jeanbarrossilva.tick.app.TickApplication
import com.jeanbarrossilva.tick.app.demo.extensions.addGroups
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class DemoTickApplication : TickApplication() {
    override fun onCreate() {
        super.onCreate()
        MainScope().launch { addSamplesOnAbsence() }
    }

    private suspend fun addSamplesOnAbsence() {
        val repository by inject<ToDoRepository>()
        val areAbsent = repository.fetch().first().isEmpty()
        if (areAbsent) {
            addSamples()
        }
    }

    private suspend fun addSamples() {
        val editor by inject<ToDoEditor>()
        editor.addGroups(ToDoGroup.samples)
    }
}
