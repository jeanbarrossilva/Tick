package com.jeanbarrossilva.tick.feature.composer

import com.jeanbarrossilva.tick.feature.composer.extensions.selectFirst
import com.jeanbarrossilva.tick.feature.composer.selectable.SelectableList
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDo
import com.jeanbarrossilva.tick.feature.todos.ui.group.ToDoGroupDescription
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
            groups = ToDoGroupDescription.samples.map(ToDoGroupDescription::title).selectFirst()
        )
    }
}
