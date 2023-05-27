package com.jeanbarrossilva.tick.core.todo.room.infra

import android.content.Context
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupEntity
import com.jeanbarrossilva.tick.core.todo.room.domain.group.RoomToDoGroupScope
import com.jeanbarrossilva.tick.std.launchable.isFirstLaunch
import java.lang.ref.WeakReference
import java.util.UUID
import kotlinx.coroutines.launch

class RoomToDoEditor(
    private val contextRef: WeakReference<Context>,
    private val database: RoomToDoDatabase,
    override val repository: ToDoRepository
) : ToDoEditor() {
    constructor(context: Context, database: RoomToDoDatabase, repository: ToDoRepository) :
        this(WeakReference(context), database, repository)

    init {
        addDefaultGroupOnFirstLaunch()
    }

    override suspend fun onAddGroup(id: UUID, title: String) {
        val group = RoomToDoGroupEntity("$id", title)
        database.toDoGroupDao.insert(group)
    }

    override fun getGroupScope(id: UUID): ToDoGroupScope {
        return RoomToDoGroupScope(database.toDoDao, repository, id)
    }

    private fun addDefaultGroupOnFirstLaunch() {
        val context = contextRef.get() ?: return
        if (context.isFirstLaunch) {
            addDefaultGroup()
        }
    }

    private fun addDefaultGroup() {
        database.coroutineScope.launch {
            addGroup(DEFAULT_GROUP_TITLE)
        }
    }

    companion object {
        internal const val DEFAULT_GROUP_TITLE = "General"
    }
}
