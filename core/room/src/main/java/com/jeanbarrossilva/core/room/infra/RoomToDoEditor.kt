package com.jeanbarrossilva.core.room.infra

import android.content.Context
import com.jeanbarrossilva.core.room.domain.RoomToDoDao
import com.jeanbarrossilva.core.room.domain.group.RoomToDoGroupDao
import com.jeanbarrossilva.core.room.domain.group.RoomToDoGroupEntity
import com.jeanbarrossilva.core.room.domain.group.RoomToDoGroupScope
import com.jeanbarrossilva.tick.core.domain.group.ToDoGroupScope
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.infra.ToDoRepository
import com.jeanbarrossilva.tick.platform.launchable.isFirstLaunch
import java.lang.ref.WeakReference
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RoomToDoEditor(
    private val contextRef: WeakReference<Context>,
    private val toDoGroupDao: RoomToDoGroupDao,
    private val toDoDao: RoomToDoDao,
    override val repository: ToDoRepository,
    private val coroutineScope: CoroutineScope
) : ToDoEditor() {
    private val onDefaultGroupAdditionListeners = mutableListOf<OnDefaultGroupAdditionListener>()
    private var hasDefaultGroupBeenAdded = false

    constructor(
        context: Context,
        toDoGroupDao: RoomToDoGroupDao,
        toDoDao: RoomToDoDao,
        repository: ToDoRepository,
        coroutineScope: CoroutineScope
    ) : this(WeakReference(context), toDoGroupDao, toDoDao, repository, coroutineScope)

    internal fun interface OnDefaultGroupAdditionListener {
        suspend fun onDefaultGroupAddition()
    }

    init {
        addDefaultGroup()
    }

    override suspend fun onAddGroup(id: UUID, title: String) {
        val group = RoomToDoGroupEntity("$id", title)
        toDoGroupDao.insert(group)
    }

    override fun getGroupScope(id: UUID): ToDoGroupScope {
        return RoomToDoGroupScope(toDoDao, repository, id)
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
