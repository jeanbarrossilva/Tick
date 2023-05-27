package com.jeanbarrossilva.core.room.test

import android.app.Application
import com.jeanbarrossilva.tick.platform.launchable.Launchable

internal class RoomToDoApplication : Application(), Launchable {
    override fun count(): Int {
        return 0
    }

    override fun markAsLaunched() {
    }
}
