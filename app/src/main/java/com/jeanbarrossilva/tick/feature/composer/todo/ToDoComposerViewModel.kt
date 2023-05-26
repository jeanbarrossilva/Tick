package com.jeanbarrossilva.tick.feature.composer.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.await
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.mutableStateIn
import com.jeanbarrossilva.tick.feature.reminder.ReminderWorker
import com.jeanbarrossilva.tick.std.selectable.select
import com.jeanbarrossilva.tick.std.selectable.selectFirst
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ToDoComposerViewModel internal constructor(
    application: Application,
    private val repository: ToDoRepository,
    private val editor: ToDoEditor
) : AndroidViewModel(application) {
    private val titleMutableFlow = MutableStateFlow("")
    private val dueDateTimeMutableFlow = MutableStateFlow<LocalDateTime?>(null)

    internal val groupsLoadableFlow = repository
        .fetch()
        .map(List<ToDoGroup>::selectFirst)
        .loadable(viewModelScope)
        .mutableStateIn(viewModelScope)
    internal val titleFlow = titleMutableFlow.asStateFlow()
    internal val dueDateTimeFlow = dueDateTimeMutableFlow.asStateFlow()

    internal fun setTitle(title: String) {
        titleMutableFlow.value = title
    }

    internal fun setDueDateTime(dueDateTime: LocalDateTime?) {
        dueDateTimeMutableFlow.value = dueDateTime
    }

    internal fun setGroup(group: ToDoGroup) {
        groupsLoadableFlow.value.ifLoaded {
            groupsLoadableFlow.value = Loadable.Loaded(select(group))
        }
    }

    internal fun save() {
        viewModelScope.launch {
            addToDo()?.also {
                setUpReminderIfNotAbsent(it)
            }
        }
    }

    private suspend fun addToDo(): UUID? {
        return groupsLoadableFlow.value.ifLoaded {
            editor.onGroup(selected.id).addToDo(titleFlow.value, dueDateTimeFlow.value)
        }
    }

    private suspend fun setUpReminderIfNotAbsent(toDoID: UUID) {
        val toDo = repository.fetch(toDoID).filterNotNull().first()
        val hasReminder = toDo.dueDateTime != null
        if (hasReminder) {
            setUpReminder(toDo)
        }
    }

    private suspend fun setUpReminder(toDo: ToDo) {
        val application = getApplication<Application>()
        val workName = ReminderWorker.getName(toDo)
        val workRequest = ReminderWorker.request(toDo)
        WorkManager
            .getInstance(application)
            .enqueueUniqueWork(workName, ExistingWorkPolicy.KEEP, workRequest)
            .await()
    }

    companion object {
        fun createFactory(application: Application, repository: ToDoRepository, editor: ToDoEditor):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ToDoComposerViewModel::class) {
                    ToDoComposerViewModel(application, repository, editor)
                }
            }
        }
    }
}
