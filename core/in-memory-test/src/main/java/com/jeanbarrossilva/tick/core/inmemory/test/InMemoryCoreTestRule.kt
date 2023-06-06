package com.jeanbarrossilva.tick.core.inmemory.test

import com.jeanbarrossilva.tick.core.inmemory.infra.InMemoryToDoEditor
import com.jeanbarrossilva.tick.core.inmemory.infra.InMemoryToDoRepository
import com.jeanbarrossilva.tick.core.test.CoreTestRule

class InMemoryCoreTestRule : CoreTestRule() {
    override val repository = InMemoryToDoRepository()
    override val editor = InMemoryToDoEditor(repository)
}
