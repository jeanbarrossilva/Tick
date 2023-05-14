package com.jeanbarrossilva.tick.core.todo.sample.infra

import com.jeanbarrossilva.tick.core.todo.domain.ToDoGroup
import com.jeanbarrossilva.tick.core.todo.infra.ToDoGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SampleToDoGroupRepository : ToDoGroupRepository {
    override fun fetch(): Flow<List<ToDoGroup>> {
        return flowOf(ToDoGroup.samples)
    }
}
