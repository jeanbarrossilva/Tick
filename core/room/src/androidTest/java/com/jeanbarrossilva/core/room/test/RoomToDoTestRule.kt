package com.jeanbarrossilva.core.room.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.core.room.infra.RoomToDoDatabase
import com.jeanbarrossilva.core.room.infra.RoomToDoEditor
import com.jeanbarrossilva.core.room.infra.RoomToDoRepository
import kotlinx.coroutines.CoroutineScope
import org.junit.rules.ExternalResource

internal class RoomToDoTestRule(private val coroutineScope: CoroutineScope) : ExternalResource() {
    private val database by lazy {
        Room.inMemoryDatabaseBuilder(context, RoomToDoDatabase::class.java).build()
    }

    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    val repository by lazy { RoomToDoRepository(database.toDoGroupDao, database.toDoDao) }
    val editor by lazy {
        RoomToDoEditor(context, database.toDoGroupDao, database.toDoDao, repository, coroutineScope)
    }

    override fun after() {
        database.clearAllTables()
        database.close()
    }
}