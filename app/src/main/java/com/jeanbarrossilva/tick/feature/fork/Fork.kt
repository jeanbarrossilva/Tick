package com.jeanbarrossilva.tick.feature.fork

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.feature.fork.ui.option.group.OptionGroup
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
fun Fork(
    onNavigationToToDoComposer: () -> Unit,
    onNavigationToGroupComposer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
        OptionGroup(
            Modifier.navigationBarsPadding(),
            contentPadding = PaddingValues(TickTheme.sizes.large)
        ) {
            option(id = "add-to-do", onClick = onNavigationToToDoComposer) {
                Text("Add to-do")
            }

            option(id = "add-group", onClick = onNavigationToGroupComposer) {
                Text("Add group")
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ForkPreview() {
    TickTheme {
        Fork(onNavigationToToDoComposer = { }, onNavigationToGroupComposer = { })
    }
}
