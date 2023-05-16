package com.jeanbarrossilva.tick.app.extensions

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.tick.core.todo.infra.ToDoEditor
import com.jeanbarrossilva.tick.core.todo.infra.ToDoRepository
import com.jeanbarrossilva.tick.feature.composer.Composer
import com.jeanbarrossilva.tick.feature.composer.ComposerViewModel
import com.jeanbarrossilva.tick.feature.todos.ToDos
import com.jeanbarrossilva.tick.feature.todos.ToDosViewModel
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import org.koin.compose.koinInject

/** Adds [Composer]' dependencies to this [DependenciesContainerBuilder].. **/
@Composable
@Suppress("ComposableNaming")
internal fun DependenciesContainerBuilder<*>.composerDependencies() {
    val repository = koinInject<ToDoRepository>()
    val editor = koinInject<ToDoEditor>()
    val viewModelFactory = ComposerViewModel.createFactory(repository, editor)
    val viewModel = viewModel<ComposerViewModel>(factory = viewModelFactory)
    dependency(viewModel)
}

/** Adds [ToDos]' dependencies to this [DependenciesContainerBuilder].. **/
@Composable
@Suppress("ComposableNaming")
internal fun DependenciesContainerBuilder<*>.toDosDependencies() {
    val repository = koinInject<ToDoRepository>()
    val editor = koinInject<ToDoEditor>()
    val viewModelFactory = ToDosViewModel.createFactory(repository, editor)
    val viewModel = viewModel<ToDosViewModel>(factory = viewModelFactory)
    dependency(viewModel)
}
