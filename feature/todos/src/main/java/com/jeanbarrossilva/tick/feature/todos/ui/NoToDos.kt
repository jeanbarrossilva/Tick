package com.jeanbarrossilva.tick.feature.todos.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun NoToDos(modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(TickTheme.spacings.large)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Looks like you have no to-dos yet.", style = TickTheme.typography.displayLarge)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NoToDosPreview() {
    TickTheme {
        Surface(color = TickTheme.colorScheme.background) {
            NoToDos()
        }
    }
}
