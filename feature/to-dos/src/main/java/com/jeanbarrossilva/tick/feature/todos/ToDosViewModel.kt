package com.jeanbarrossilva.tick.feature.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.loadable.list.flow.listLoadable
import com.jeanbarrossilva.loadable.list.ifPopulated
import com.jeanbarrossilva.loadable.list.serialize
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.domain.group.of
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import com.jeanbarrossilva.tick.feature.todos.extensions.filterNotEmpty
import java.util.UUID
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ToDosViewModel internal constructor(
    repository: ToDoRepository,
    private val editor: ToDoEditor
) : ViewModel() {
    internal val groupsListLoadableFlow = repository
        .fetch()
        .map(List<ToDoGroup>::filterNotEmpty)
        .map(List<ToDoGroup>::serialize)
        .listLoadable(viewModelScope, SharingStarted.WhileSubscribed())

    internal fun toggle(toDoID: UUID, isDone: Boolean) {
        groupsListLoadableFlow.value.ifPopulated {
            viewModelScope.launch {
                val (group, _) = this@ifPopulated of toDoID
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
