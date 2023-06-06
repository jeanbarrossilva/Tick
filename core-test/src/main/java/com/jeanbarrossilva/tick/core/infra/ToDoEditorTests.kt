package com.jeanbarrossilva.tick.core.infra

import app.cash.turbine.test
import com.jeanbarrossilva.tick.core.domain.get
import com.jeanbarrossilva.tick.core.domain.group.get
import com.jeanbarrossilva.tick.core.test.CoreTestRule
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest

abstract class ToDoEditorTests {
    abstract fun addsGroup()

    abstract fun addsToDo()

    abstract fun clears()

    protected fun addsGroup(coreRule: CoreTestRule) {
        runTest {
            val groupID = coreRule.editor.addGroup("G1")
            coreRule.repository.fetch().map { groups -> groups[groupID] }.test {
                assertNotNull(awaitItem())
            }
        }
    }

    protected fun addsToDo(coreRule: CoreTestRule) {
        runTest {
            val groupID = coreRule.editor.addGroup("G1")
            val toDoID = coreRule.editor.onGroup(groupID).addToDo("G1-T1", dueDateTime = null)
            coreRule
                .repository
                .fetch()
                .map { groups -> groups[groupID]?.toDos()?.get(toDoID) }
                .test { assertNotNull(awaitItem()) }
        }
    }

    protected fun clears(coreRule: CoreTestRule) {
        runTest {
            val firstGroupID = coreRule.editor.addGroup("G1")
            val secondGroupID = coreRule.editor.addGroup("G2")
            coreRule.editor.onGroup(firstGroupID).addToDo("G1-T1", dueDateTime = null)
            coreRule.editor.onGroup(firstGroupID).addToDo("G1-T2", dueDateTime = null)
            coreRule.editor.onGroup(secondGroupID).addToDo("G2-T1", dueDateTime = null)
            coreRule.editor.onGroup(secondGroupID).addToDo("G2-T2", dueDateTime = null)
            coreRule.editor.clear()
            coreRule.repository.fetch().test { assertContentEquals(emptyList(), awaitItem()) }
        }
    }
}
