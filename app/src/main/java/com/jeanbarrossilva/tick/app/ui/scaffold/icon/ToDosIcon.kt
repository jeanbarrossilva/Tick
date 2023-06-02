package com.jeanbarrossilva.tick.app.ui.scaffold.icon

import android.content.res.Configuration
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun ToDosIcon(modifier: Modifier = Modifier) {
    Icon(TickTheme.Icons.CheckCircle, contentDescription = "To-dos", modifier)
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDosIconPreview() {
    TickTheme {
        ToDosIcon()
    }
}
