package com.jeanbarrossilva.tick.core.todo.room.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RoomToDoDao internal constructor() {
    @Query("SELECT * FROM to_dos")
    internal abstract fun selectAll(): Flow<List<RoomToDoEntity>>

    @Query("SELECT * FROM to_dos WHERE id = :id")
    internal abstract fun selectByID(id: String): Flow<RoomToDoEntity?>

    @Insert
    internal abstract suspend fun insert(toDo: RoomToDoEntity)

    @Query("UPDATE to_dos SET is_done = :isDone WHERE id = :id")
    internal abstract suspend fun setDone(id: String, isDone: Boolean)
}
