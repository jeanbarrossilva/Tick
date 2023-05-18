package com.jeanbarrossilva.tick.core.todo.room.infra

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeanbarrossilva.tick.core.todo.room.domain.RoomToDoDao
import com.jeanbarrossilva.tick.core.todo.room.domain.RoomToDoEntity
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupDao
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupEntity
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

@Database(entities = [RoomToDoGroupEntity::class, RoomToDoEntity::class], version = 1)
abstract class RoomToDoDatabase internal constructor() : RoomDatabase() {
    abstract val toDoGroupDao: RoomToDoGroupDao
    abstract val toDoDao: RoomToDoDao

    companion object {
        private lateinit var instance: RoomToDoDatabase

        internal fun getInstance(context: Context): RoomToDoDatabase {
            return if (::instance.isInitialized) {
                instance
            } else {
                instance = create(context)
                instance
            }
        }

        private fun create(context: Context): RoomToDoDatabase {
            return Room.databaseBuilder(context, RoomToDoDatabase::class.java, "to-do-db").build()
        }
    }
}

/** Creates or gets the existing instance of a [RoomToDoDatabase] from this [Scope]. **/
val Scope.database: RoomToDoDatabase
    get() {
        val context = androidContext()
        return RoomToDoDatabase.getInstance(context)
    }
