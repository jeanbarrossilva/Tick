package com.jeanbarrossilva.tick.feature.todos

import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoEditor
import com.jeanbarrossilva.tick.core.todo.inmemory.infra.InMemoryToDoGroupRepository
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("FunctionName")
fun ToDosModule(): Module {
    val repository = InMemoryToDoGroupRepository()
    return module {
        single<ToDoGroupRepository> { repository }
        single<ToDoEditor> { InMemoryToDoEditor(repository) }
    }
}
