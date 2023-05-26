package com.jeanbarrossilva.tick.platform.option.extensions

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize

/** This [CornerBasedShape], with zeroed top [CornerSize]s. **/
internal val CornerBasedShape.bottom
    get() = copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)

/** This [CornerBasedShape], with zeroed bottom [CornerSize]s. **/
internal val CornerBasedShape.top
    get() = copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize)
