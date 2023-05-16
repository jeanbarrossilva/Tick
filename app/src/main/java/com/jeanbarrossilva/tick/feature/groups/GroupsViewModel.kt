package com.jeanbarrossilva.tick.feature.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.std.SelectableList
import com.jeanbarrossilva.tick.std.select
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GroupsViewModel private constructor(groups: SelectableList<ToDoGroup>) : ViewModel() {
    private val groupsMutableFlow = MutableStateFlow(groups)

    internal val groupsFlow = groupsMutableFlow.asStateFlow()

    val selectedGroupFlow = groupsFlow.map { groups -> groups.selected }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    internal fun select(group: ToDoGroup) {
        groupsMutableFlow.value = groupsFlow.value.select(group)
    }

    companion object {
        fun createFactory(groups: SelectableList<ToDoGroup>): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(GroupsViewModel::class) {
                    GroupsViewModel(groups)
                }
            }
        }
    }
}
