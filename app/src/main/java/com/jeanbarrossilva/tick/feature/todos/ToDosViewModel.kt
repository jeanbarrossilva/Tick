package com.jeanbarrossilva.tick.feature.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.loadable.map
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.domain.group.of
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import com.jeanbarrossilva.tick.feature.todos.extensions.toToDosDescription
import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ToDosViewModel internal constructor(
    repository: ToDoGroupRepository,
    private val editor: ToDoEditor
) : ViewModel() {
    internal val descriptionLoadableFlow =
        repository.fetch().map(List<ToDoGroup>::toToDosDescription).loadable(viewModelScope)

    internal fun toggle(toDoID: UUID, isDone: Boolean) {
        descriptionLoadableFlow
            .value
            .map { description -> description.toDoGroups.serialize() }
            .ifLoaded {
                viewModelScope.launch {
                    val (group, _) = this@ifLoaded of toDoID
                    editor.onGroup(group.id).onToDo(toDoID).setDone(isDone)
                }
            }
    }

    companion object {
        fun createFactory(repository: ToDoGroupRepository, editor: ToDoEditor):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ToDosViewModel::class) {
                    ToDosViewModel(repository, editor)
                }
            }
        }
    }
}
