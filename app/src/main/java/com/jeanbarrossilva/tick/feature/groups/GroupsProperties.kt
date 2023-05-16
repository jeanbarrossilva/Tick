package com.jeanbarrossilva.tick.feature.groups

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import java.io.Serializable

data class GroupsProperties(
    val selectedGroup: ToDoGroup,
    val isBottomAreaAvailable: Boolean
) : Serializable
