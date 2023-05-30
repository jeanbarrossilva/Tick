package com.jeanbarrossilva.tick.core.room.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.tick.core.room.infra.RoomToDoDatabase
import com.jeanbarrossilva.tick.core.room.infra.RoomToDoEditor
import com.jeanbarrossilva.tick.core.room.infra.RoomToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.junit.rules.ExternalResource

internal class RoomToDoTestRule(private val coroutineScope: CoroutineScope) : ExternalResource() {
    private val database by lazy {
        Room.inMemoryDatabaseBuilder(context, RoomToDoDatabase::class.java).build()
    }

    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    val repository by lazy { RoomToDoRepository(database.toDoGroupDao, database.toDoDao) }
    val editor by lazy { RoomToDoEditor(context, database, repository, coroutineScope) }

    override fun after() {
        coroutineScope.launch { editor.clear() }
        database.close()
    }
}
