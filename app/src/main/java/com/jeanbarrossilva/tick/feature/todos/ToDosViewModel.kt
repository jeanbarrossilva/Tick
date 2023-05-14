package com.jeanbarrossilva.tick.feature.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.map
import com.jeanbarrossilva.tick.core.todo.domain.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import com.jeanbarrossilva.tick.feature.todos.extensions.get
import com.jeanbarrossilva.tick.feature.todos.extensions.replacing
import com.jeanbarrossilva.tick.feature.todos.extensions.replacingBy
import com.jeanbarrossilva.tick.feature.todos.extensions.toMutableStateFlow
import com.jeanbarrossilva.tick.feature.todos.extensions.toToDosDescription
import java.util.UUID
import kotlinx.coroutines.flow.map

class ToDosViewModel internal constructor(toDoGroupRepository: ToDoGroupRepository) :
    ViewModel() {
    internal val descriptionLoadableFlow = toDoGroupRepository
        .fetch()
        .map(List<ToDoGroup>::toToDosDescription)
        .loadable(viewModelScope)
        .toMutableStateFlow()

    internal fun toggle(toDoID: UUID, isDone: Boolean) {
        descriptionLoadableFlow.value = descriptionLoadableFlow.value.map { description ->
            val (toDoGroupDescription, toDo) =
                requireNotNull(description.toDoGroups[toDoID])
            val toggledToDos =
                toDoGroupDescription.toDos.replacing(toDoID) { toDo.copy(isDone = isDone) }
            val toggledToDoDescription = toDoGroupDescription.copy(toDos = toggledToDos)
            val toggledToDoGroupDescriptions = description
                .toDoGroups
                .replacingBy({ toggledToDoDescription }, toDoGroupDescription::equals)
            description.copy(toDoGroups = toggledToDoGroupDescriptions)
        }
    }

    companion object {
        fun createFactory(toDoGroupRepository: ToDoGroupRepository): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ToDosViewModel::class) {
                    ToDosViewModel(toDoGroupRepository)
                }
            }
        }
    }
}
