package com.jeanbarrossilva.tick.core.domain

interface ToDoScope {
    suspend fun setDone(isDone: Boolean)
}
