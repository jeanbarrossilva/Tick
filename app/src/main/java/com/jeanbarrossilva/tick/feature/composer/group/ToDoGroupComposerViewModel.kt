package com.jeanbarrossilva.tick.feature.composer.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.tick.core.infra.ToDoEditor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToDoGroupComposerViewModel internal constructor(private val editor: ToDoEditor) :
    ViewModel() {
    private val titleMutableFlow = MutableStateFlow("")

    internal val titleFlow = titleMutableFlow.asStateFlow()

    internal fun setTitle(title: String) {
        titleMutableFlow.value = title
    }

    internal fun save() {
        viewModelScope.launch {
            editor.addGroup(titleFlow.value)
        }
    }

    companion object {
        fun createFactory(editor: ToDoEditor): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ToDoGroupComposerViewModel::class) {
                    ToDoGroupComposerViewModel(editor)
                }
            }
        }
    }
}
