package com.jeanbarrossilva.tick.app.demo.extensions

import com.jeanbarrossilva.tick.core.todo.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor

/**
 * Adds the [groups] and all of their [to-dos][ToDoGroup.toDos].
 *
 * @param groups [ToDoGroup]s to be added.
 **/
internal suspend fun ToDoEditor.addGroups(groups: List<ToDoGroup>) {
    groups.forEach { group ->
        addGroup(group)
    }
}

/**
 * Adds the [group] and all of its [to-dos][ToDoGroup.toDos].
 *
 * @param group [ToDoGroup] to be added.
 **/
private suspend fun ToDoEditor.addGroup(group: ToDoGroup) {
    val id = addGroup(group.title)
    onGroup(id).addToDos(group.toDos())
}
