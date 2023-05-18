package com.jeanbarrossilva.tick.app

import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.todo.room.infra.RoomToDoEditor
import com.jeanbarrossilva.tick.core.todo.room.infra.RoomToDoRepository
import com.jeanbarrossilva.tick.core.todo.room.infra.database
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("FunctionName")
internal fun TickModule(): Module {
    return module {
        single<ToDoRepository> { RoomToDoRepository(database.toDoGroupDao, database.toDoDao) }
        single<ToDoEditor> {
            RoomToDoEditor(database.toDoGroupDao, database.toDoDao, repository = get())
        }
    }
}
