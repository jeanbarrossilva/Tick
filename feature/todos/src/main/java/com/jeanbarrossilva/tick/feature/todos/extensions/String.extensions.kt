package com.jeanbarrossilva.tick.feature.todos.extensions

import java.util.Locale

/** Capitalized version of this [String]. **/
internal val String.capitalized
    get() = replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
