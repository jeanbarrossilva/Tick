package com.jeanbarrossilva.core.room.infra

import app.cash.turbine.test
import com.jeanbarrossilva.core.room.test.RoomToDoTestRule
import com.jeanbarrossilva.tick.core.domain.ToDo
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.core.domain.group.get
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class RoomToDoRepositoryTests {
    private val coroutineScope = TestScope()

    @get:Rule
    val toDoRule = RoomToDoTestRule(coroutineScope)

    @Test
    fun fetches() {
        coroutineScope.runTest {
            val groupAID = toDoRule.editor.addGroup("A")
            val groupBID = toDoRule.editor.addGroup("B")
            toDoRule.editor.onGroup(groupAID).addToDo("1", dueDateTime = null)
            toDoRule.editor.onGroup(groupBID).addToDo("2", dueDateTime = null)
            toDoRule.repository.fetch().test {
                val groups = awaitItem()
                println(groups)
                assertEquals("A", groups[groupAID]?.title)
                assertEquals("1", groups[groupAID]?.toDos()?.first()?.title)
                assertEquals("2", groups[groupBID]?.toDos()?.first()?.title)
            }
        }
    }

    @Test
    fun fetchesWithDefaultGroup() {
        toDoRule.editor

        coroutineScope.runTest {
            toDoRule.editor.doOnDefaultGroupAddition {
                toDoRule.repository.fetch().map(List<ToDoGroup>::first).test {
                    val group = awaitItem()
                    assertEquals(RoomToDoEditor.DEFAULT_GROUP_TITLE, group.title)
                    assertEquals(emptyList<ToDo>(), group.toDos())
                }
            }
        }
    }
}
