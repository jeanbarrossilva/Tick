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
    val groups: SelectableList<String>
) : Serializable {
    companion object {
        val sample = ComposerDescription(
            title = ToDo.sample.title,
            dueDateTime = ToDo.sample.dueDateTime,
            groups = ToDoGroup.samples.map(ToDoGroup::title).selectFirst()
        )
    }
}
