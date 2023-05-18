package com.jeanbarrossilva.tick.core.todo.room.infra

import app.cash.turbine.test
import com.jeanbarrossilva.tick.core.todo.room.test.RoomToDoTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class RoomToDoRepositoryTests {
    @get:Rule
    val toDoRule = RoomToDoTestRule()

    @Test
    fun fetches() {
        runTest {
            val groupAID = toDoRule.editor.addGroup("A")
            val groupBID = toDoRule.editor.addGroup("B")
            toDoRule.editor.onGroup(groupAID).addToDo("1", dueDateTime = null)
            toDoRule.editor.onGroup(groupBID).addToDo("2", dueDateTime = null)
            toDoRule.repository.fetch().test {
                val groups = awaitItem()
                assertEquals("A", groups.first().title)
                assertEquals("1", groups.first().toDos().first().title)
                assertEquals("2", groups.last().toDos().first().title)
            }
        }
    }
}
