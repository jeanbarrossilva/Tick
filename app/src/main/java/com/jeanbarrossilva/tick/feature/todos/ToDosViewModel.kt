package com.jeanbarrossilva.tick.feature.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.innerMap
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.domain.group.of
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToDosViewModel internal constructor(
    repository: ToDoRepository,
    private val editor: ToDoEditor
) : ViewModel() {
    internal val groupsLoadableFlow =
        repository.fetch().map(List<ToDoGroup>::serialize).loadable(viewModelScope)
    internal val ongoingToDoLoadable = groupsLoadableFlow
        .innerMap { groups ->
            groups.flatMap(ToDoGroup::toDos).first { toDo ->
                toDo.dueDateTime != null && toDo.dueDateTime >= LocalDateTime.now()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loadable.Loading())

    internal fun toggle(toDoID: UUID, isDone: Boolean) {
        groupsLoadableFlow.value.ifLoaded {
            viewModelScope.launch {
                val (group, _) = this@ifLoaded of toDoID
                editor.onGroup(group.id).onToDo(toDoID).setDone(isDone)
            }
        }
    }

    companion object {
        fun createFactory(repository: ToDoRepository, editor: ToDoEditor):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ToDosViewModel::class) {
                    ToDosViewModel(repository, editor)
                }
            }
        }
    }
}
