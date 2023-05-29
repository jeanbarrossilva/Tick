package com.jeanbarrossilva.tick.feature.todos.extensions

import java.time.LocalDateTime
import org.ocpsoft.prettytime.PrettyTime

/** Relatively-formatted version of this [LocalDateTime]. **/
internal val LocalDateTime.relative: String
    get() = PrettyTime().format(this)
