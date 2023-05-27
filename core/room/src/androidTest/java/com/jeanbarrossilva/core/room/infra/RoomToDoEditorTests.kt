package com.jeanbarrossilva.core.room.infra

import app.cash.turbine.test
import com.jeanbarrossilva.core.room.test.RoomToDoTestRule
import com.jeanbarrossilva.tick.core.domain.group.get
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

internal class RoomToDoEditorTests {
    private val coroutineScope = TestScope()

    @get:Rule
    val toDoRule = RoomToDoTestRule(coroutineScope)

    @Test
    fun addsToDoGroup() {
        coroutineScope.runTest {
            val id = toDoRule.editor.addGroup("A")
            toDoRule.repository.fetch().map { groups -> groups[id] }.test {
                assertNotNull(awaitItem())
            }
        }
    }

    @Test
    fun addsToDos() {
        coroutineScope.runTest {
            val groupID = toDoRule.editor.addGroup("A")
            toDoRule.editor.onGroup(groupID).addToDo(title = "B", dueDateTime = null)
            toDoRule.repository.fetch().map { groups -> groups[groupID]?.toDos()?.first() }.test {
                assertEquals("B", awaitItem()?.title)
            }
        }
    }
}
