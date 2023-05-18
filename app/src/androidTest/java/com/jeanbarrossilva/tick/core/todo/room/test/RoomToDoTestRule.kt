package com.jeanbarrossilva.tick.core.todo.room.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.tick.core.todo.room.infra.RoomToDoDatabase
import com.jeanbarrossilva.tick.core.todo.room.infra.RoomToDoEditor
import com.jeanbarrossilva.tick.core.todo.room.infra.RoomToDoRepository
import org.junit.rules.ExternalResource

internal class RoomToDoTestRule : ExternalResource() {
    private val database by lazy {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Room.inMemoryDatabaseBuilder(context, RoomToDoDatabase::class.java).build()
    }

    val repository by lazy { RoomToDoRepository(database.toDoGroupDao, database.toDoDao) }
    val editor by lazy { RoomToDoEditor(database.toDoGroupDao, database.toDoDao, repository) }

    override fun after() {
        database.clearAllTables()
        database.close()
    }
}
