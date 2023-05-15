package com.jeanbarrossilva.tick.core.todo.domain

interface ToDoScope {
    suspend fun setDone(isDone: Boolean)
}
