package com.jeanbarrossilva.tick.core.todo.room.domain.group

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RoomToDoGroupDao internal constructor() {
    @Query("SELECT * FROM groups")
    internal abstract fun selectAll(): Flow<List<RoomToDoGroupEntity>>

    @Insert
    internal abstract suspend fun insert(group: RoomToDoGroupEntity)
}
