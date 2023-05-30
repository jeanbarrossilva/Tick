package com.jeanbarrossilva.tick.feature.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.feature.settings.ui.ResetDialog
import com.jeanbarrossilva.tick.platform.theme.TickTheme
import com.jeanbarrossilva.tick.platform.theme.extensions.plus
import com.jeanbarrossilva.tick.platform.theme.ui.setting.Setting

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Settings(
    appName: String,
    versionName: String,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isResetDialogVisible by remember { mutableStateOf(false) }

    if (isResetDialogVisible) {
        ResetDialog(onConfirmation = onReset, onDismissal = { isResetDialogVisible = false })
    }

    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") },
                scrollBehavior = topAppBarScrollBehavior
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            contentPadding = padding + PaddingValues(TickTheme.spacings.large)
        ) {
            item {
                Setting(
                    text = { Text("Clears all of the added to-dos.") },
                    action = { Icon(TickTheme.Icons.DeleteForever, contentDescription = "Reset") },
                    onClick = { isResetDialogVisible = true },
                    containerColor = TickTheme.colorScheme.error,
                    label = { Text("Reset") }
                )
            }

            item {
                Column(
                    Modifier
                        .padding(top = TickTheme.spacings.extraLarge)
                        .fillMaxWidth(),
                    Arrangement.spacedBy(TickTheme.spacings.extraSmall),
                    Alignment.CenterHorizontally
                ) {
                    ProvideTextStyle(
                        LocalTextStyle.current.copy(color = TickTheme.colorScheme.surfaceTint)
                    ) {
                        Text(
                            appName,
                            textAlign = TextAlign.Center,
                            style = LocalTextStyle.current + TickTheme.typography.bodyLarge
                        )

                        Text(versionName, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SettingsPreview() {
    TickTheme {
        Settings(appName = "Tick", versionName = "1.0.0", onReset = { })
    }
}
