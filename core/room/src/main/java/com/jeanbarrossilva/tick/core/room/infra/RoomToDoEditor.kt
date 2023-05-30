package com.jeanbarrossilva.tick.core.room.infra

import android.content.Context
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import com.jeanbarrossilva.tick.core.room.domain.group.RoomToDoGroupEntity
import com.jeanbarrossilva.tick.core.room.domain.group.RoomToDoGroupScope
import com.jeanbarrossilva.tick.platform.launchable.isFirstLaunch
import java.lang.ref.WeakReference
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomToDoEditor internal constructor(
    private val contextRef: WeakReference<Context>,
    private val database: RoomToDoDatabase,
    override val repository: ToDoRepository,
    private val coroutineScope: CoroutineScope
) : ToDoEditor() {
    private val onDefaultGroupAdditionListeners = mutableListOf<OnDefaultGroupAdditionListener>()
    private var hasDefaultGroupBeenAdded = false

    constructor(context: Context, database: RoomToDoDatabase, repository: ToDoRepository) :
        this(context, database, repository, database.coroutineScope)

    internal constructor(
        context: Context,
        database: RoomToDoDatabase,
        repository: ToDoRepository,
        coroutineScope: CoroutineScope
    ) : this(WeakReference(context), database, repository, coroutineScope)

    internal fun interface OnDefaultGroupAdditionListener {
        suspend fun onDefaultGroupAddition()
    }

    init {
        addDefaultGroup()
    }

    override suspend fun onAddGroup(id: UUID, title: String) {
        val group = RoomToDoGroupEntity("$id", title)
        database.toDoGroupDao.insert(group)
    }

    override fun getGroupScope(id: UUID): ToDoGroupScope {
        return RoomToDoGroupScope(database.toDoDao, repository, id)
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

    internal fun doOnDefaultGroupAddition(listener: OnDefaultGroupAdditionListener) {
        if (hasDefaultGroupBeenAdded) {
            notify(listener)
        } else {
            onDefaultGroupAdditionListeners.add(listener)
        }
    }

    private fun addDefaultGroup() {
        coroutineScope.launch { addDefaultGroupOnFirstLaunch() }.invokeOnCompletion { error ->
            if (error == null) {
                notifyDefaultGroupAddition()
            }
        }
    }

    private suspend fun addDefaultGroupOnFirstLaunch() {
        val context = contextRef.get() ?: return
        if (context.isFirstLaunch) {
            addGroup(DEFAULT_GROUP_TITLE)
            hasDefaultGroupBeenAdded = true
        }
    }

    private fun notifyDefaultGroupAddition() {
        onDefaultGroupAdditionListeners.forEach(::notify)
        onDefaultGroupAdditionListeners.clear()
    }

    private fun notify(listener: OnDefaultGroupAdditionListener) {
        coroutineScope.launch {
            listener.onDefaultGroupAddition()
        }
    }

    companion object {
        internal const val DEFAULT_GROUP_TITLE = "General"
    }
}
