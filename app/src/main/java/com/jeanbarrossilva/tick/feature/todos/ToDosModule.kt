package com.jeanbarrossilva.tick.feature.todos

import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoEditor
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoRepository
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("FunctionName")
fun ToDosModule(): Module {
    val repository = InMemoryToDoRepository()
    return module {
        single<ToDoRepository> { repository }
        single<ToDoEditor> { InMemoryToDoEditor(repository) }
    }
}
