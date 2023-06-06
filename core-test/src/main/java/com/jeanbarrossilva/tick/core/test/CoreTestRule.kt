package com.jeanbarrossilva.tick.core.test

import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import kotlinx.coroutines.test.runTest
import org.junit.rules.ExternalResource

abstract class CoreTestRule : ExternalResource() {
    abstract val repository: ToDoRepository
    abstract val editor: ToDoEditor

    override fun after() {
        runTest {
            editor.clear()
        }
    }
}
