package com.jeanbarrossilva.tick.core.todo.domain

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class ToDoGroup internal constructor(val title: String, val toDos: List<ToDo>) : Serializable {
    companion object {
        val sample = ToDoGroup(
            title = "Travels",
            toDos = listOf(
                ToDo.sample,
                ToDo(
                    UUID.randomUUID(),
                    title = "Travel to Spain",
                    LocalDateTime.now().plusYears(1),
                    isDone = false
                ),
                ToDo(
                    UUID.randomUUID(),
                    title = "Travel to France",
                    LocalDateTime.now().plusYears(1).minusMonths(1),
                    isDone = false
                )
            )
        )
        val samples = listOf(
            sample,
            ToDoGroup(
                title = "Housework",
                toDos = listOf(
                    ToDo(
                        UUID.randomUUID(),
                        title = "Clean up the kitchen",
                        LocalDateTime.now().plusHours(1),
                        isDone = true
                    ),
                    ToDo(
                        UUID.randomUUID(),
                        title = "Clean up my room",
                        LocalDateTime.now().plusHours(2),
                        isDone = false
                    ),
                    ToDo(
                        UUID.randomUUID(),
                        title = "Switch mattress",
                        LocalDateTime.now().plusHours(3),
                        isDone = false
                    )
                )
            )
        )
    }
}
