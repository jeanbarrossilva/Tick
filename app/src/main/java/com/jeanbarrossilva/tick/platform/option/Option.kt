package com.jeanbarrossilva.tick.platform.option

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus

/** Default values of an [Option]. **/
internal object OptionDefaults {
    /** [Shape] by which an [Option] is clipped by default. **/
    val shape
        @Composable get() = TickTheme.shapes.large
}

/**
 * Represents an action that can be taken by the user.
 *
 * @param icon [Icon] that visually explains the action alongside the [label].
 * @param label [Text] that briefly explains the action this [Option] represents.
 * @param onClick Lambda to be run whenever a click occurs.
 * @param modifier [Modifier] to be applied to the underlying [FilledTonalButton].
 * @param shape [Shape] by which the [Option] is clipped.
 **/
@Composable
@JvmName("OptionWithRequiredIcon")
fun Option(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = OptionDefaults.shape
) {
    Option(icon as (@Composable () -> Unit)?, label, onClick, modifier, shape)
}

/**
 * Represents an action that can be taken by the user.
 *
 * @param onClick Lambda to be run whenever a click occurs.
 * @param modifier [Modifier] to be applied to the underlying [FilledTonalButton].
 * @param shape [Shape] by which the [Option] is clipped.
 * @param label [Text] that briefly explains the action this [Option] represents.
 **/
@Composable
fun Option(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = OptionDefaults.shape,
    label: @Composable () -> Unit
) {
    Option(icon = null, label, onClick, modifier, shape)
}

/**
 * Represents an action that can be taken by the user.
 *
 * @param icon [Icon] that visually explains the action alongside the [label].
 * @param label [Text] that briefly explains the action this [Option] represents.
 * @param onClick Lambda to be run whenever a click occurs.
 * @param modifier [Modifier] to be applied to the underlying [FilledTonalButton].
 * @param shape [Shape] by which the [Option] is clipped.
 **/
@Composable
internal fun Option(
    icon: (@Composable () -> Unit)?,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = OptionDefaults.shape
) {
    val spacing = TickTheme.spacings.medium
    val horizontalArrangement = remember(icon as Any?, spacing) {
        icon?.let { Arrangement.spacedBy(spacing) } ?: Arrangement.Center
    }

    FilledTonalButton(
        onClick,
        modifier,
        shape = shape,
        contentPadding = ButtonDefaults.ContentPadding + PaddingValues(vertical = spacing)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement, Alignment.CenterVertically) {
            icon?.invoke()
            label()
        }
    }
}

/** Preview of an [Option] with an icon. **/
@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OptionWithIconPreview() {
    TickTheme {
        Option(
            label = { Text("Option") },
            icon = { Icon(TickTheme.Icons.AccountBox, contentDescription = "Icon") },
            onClick = { }
        )
    }
}

/** Preview of an [Option] without an icon. **/
@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OptionWithoutIconPreview() {
    TickTheme {
        Option(onClick = { }) {
            Text("Option")
        }
    }
}
