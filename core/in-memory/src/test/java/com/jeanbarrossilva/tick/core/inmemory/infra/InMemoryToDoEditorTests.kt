package com.jeanbarrossilva.tick.core.inmemory.infra

import com.jeanbarrossilva.tick.core.infra.ToDoEditorTests
import com.jeanbarrossilva.tick.core.inmemory.test.InMemoryCoreTestRule
import org.junit.Rule
import org.junit.Test

internal class InMemoryToDoEditorTests : ToDoEditorTests() {
    @get:Rule
    val coreRule = InMemoryCoreTestRule()

    @Test
    override fun addsGroup() {
        addsGroup(coreRule)
    }

    @Test
    override fun addsToDo() {
        addsToDo(coreRule)
    }

    @Test
    override fun clears() {
        clears(coreRule)
    }
}
