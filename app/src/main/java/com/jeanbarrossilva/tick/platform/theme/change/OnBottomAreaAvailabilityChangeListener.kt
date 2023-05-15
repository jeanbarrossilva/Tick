package com.jeanbarrossilva.tick.platform.theme.change

/**
 * Listens to changes on the availability of the utmost bottom portion of the displayed content.
 **/
fun interface OnBottomAreaAvailabilityChangeListener {
    /**
     * Callback run whenever the availability of the bottom area is changed.
     *
     * @param isAvailable Whether the bottom area is currently available.
     **/
    fun onBottomAreaAvailabilityChange(isAvailable: Boolean)
}
