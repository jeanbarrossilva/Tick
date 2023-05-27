package com.jeanbarrossilva.tick.app

import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.room.infra.RoomToDoEditor
import com.jeanbarrossilva.tick.core.room.infra.RoomToDoRepository
import com.jeanbarrossilva.tick.core.room.infra.database
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("FunctionName")
internal fun TickModule(): Module {
    return module {
        single<ToDoRepository> { RoomToDoRepository(database.toDoGroupDao, database.toDoDao) }
        single<ToDoEditor> {
            RoomToDoEditor(
                androidContext(),
                database.toDoGroupDao,
                database.toDoDao,
                repository = get(),
                database.coroutineScope
            )
        }
    }
}
