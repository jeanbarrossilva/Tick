package com.jeanbarrossilva.tick.core.todo.room.infra

import app.cash.turbine.test
import com.jeanbarrossilva.tick.core.domain.group.get
import com.jeanbarrossilva.tick.core.todo.room.test.RoomToDoTestRule
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

internal class RoomToDoEditorTests {
    @get:Rule
    val toDoRule = RoomToDoTestRule()

    @Test
    fun addsToDoGroup() {
        runTest {
            val id = toDoRule.editor.addGroup("A")
            toDoRule.repository.fetch().map { groups -> groups[id] }.test {
                assertNotNull(awaitItem())
            }
        }
    }

    @Test
    fun addsToDos() {
        runTest {
            val groupID = toDoRule.editor.addGroup("A")
            toDoRule.editor.onGroup(groupID).addToDo(title = "B", dueDateTime = null)
            toDoRule.repository.fetch().map { groups -> groups[groupID]?.toDos()?.first() }.test {
                assertEquals("B", awaitItem()?.title)
            }
        }
    }
}
