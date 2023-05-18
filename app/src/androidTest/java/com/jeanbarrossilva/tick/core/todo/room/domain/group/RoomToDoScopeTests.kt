package com.jeanbarrossilva.tick.core.todo.room.domain.group

import app.cash.turbine.test
import com.jeanbarrossilva.tick.core.todo.room.test.RoomToDoTestRule
import java.time.LocalDateTime
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class RoomToDoScopeTests {
    @get:Rule
    val toDoRule = RoomToDoTestRule()

    @Test
    fun setsDone() {
        runTest {
            val groupID = toDoRule.editor.addGroup("A")
            val dueDateTime = LocalDateTime.now()
            val toDoID = toDoRule.editor.onGroup(groupID).addToDo("1", dueDateTime)
            val isDone = true
            toDoRule.editor.onGroup(groupID).onToDo(toDoID).setDone(isDone)
            toDoRule.repository.fetch().map { groups -> groups.first().toDos().first() }.test {
                assertTrue(awaitItem().isDone)
            }
        }
    }
}
