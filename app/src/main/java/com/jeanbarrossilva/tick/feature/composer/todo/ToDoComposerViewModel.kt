package com.jeanbarrossilva.tick.feature.composer.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.feature.composer.todo.extensions.mutableStateIn
import com.jeanbarrossilva.tick.std.selectable.select
import com.jeanbarrossilva.tick.std.selectable.selectFirst
import java.time.LocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ToDoComposerViewModel internal constructor(
    repository: ToDoRepository,
    private val editor: ToDoEditor
) : ViewModel() {
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
        groupsLoadableFlow.value.ifLoaded {
            viewModelScope.launch {
                editor.onGroup(selected.id).addToDo(titleFlow.value, dueDateTimeFlow.value)
            }
        }
    }

    companion object {
        fun createFactory(repository: ToDoRepository, editor: ToDoEditor):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ToDoComposerViewModel::class) {
                    ToDoComposerViewModel(repository, editor)
                }
            }
        }
    }
}
