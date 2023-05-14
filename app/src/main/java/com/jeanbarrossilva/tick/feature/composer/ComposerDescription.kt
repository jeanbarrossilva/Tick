package com.jeanbarrossilva.tick.feature.composer

import com.jeanbarrossilva.tick.core.todo.domain.ToDo
import com.jeanbarrossilva.tick.core.todo.domain.ToDoGroup
import com.jeanbarrossilva.tick.feature.composer.extensions.selectFirst
import com.jeanbarrossilva.tick.feature.composer.selectable.SelectableList
import java.io.Serializable
import java.time.LocalDateTime

internal data class ComposerDescription(
    val title: String,
    val dueDateTime: LocalDateTime?,
    val toDoGroupNames: SelectableList<String>
) : Serializable {
    companion object {
        val sample = ComposerDescription(
            title = ToDo.sample.title,
            dueDateTime = ToDo.sample.dueDateTime,
            toDoGroupNames = ToDoGroup.samples.map(ToDoGroup::title).selectFirst()
        )
    }
}
