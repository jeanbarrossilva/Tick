package com.jeanbarrossilva.tick.feature.todos

import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import com.jeanbarrossilva.tick.core.todo.sample.infra.SampleToDoGroupRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Suppress("FunctionName")
fun ToDosModule(): Module {
    return module {
        singleOf<ToDoGroupRepository>(::SampleToDoGroupRepository)
    }
}
