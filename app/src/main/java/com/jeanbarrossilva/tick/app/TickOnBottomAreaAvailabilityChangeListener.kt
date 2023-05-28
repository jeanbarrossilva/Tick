package com.jeanbarrossilva.tick.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.jeanbarrossilva.tick.platform.theme.reactivity.OnBottomAreaAvailabilityChangeListener

internal class TickOnBottomAreaAvailabilityChangeListener :
    OnBottomAreaAvailabilityChangeListener {
    var isAvailable by mutableStateOf(false)

    override fun onBottomAreaAvailabilityChange(isAvailable: Boolean) {
        this.isAvailable = isAvailable
    }
}
