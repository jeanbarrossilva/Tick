package com.jeanbarrossilva.tick.feature.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.tick.app.R
import com.jeanbarrossilva.tick.platform.theme.TickTheme

@Composable
internal fun Settings(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val appName = stringResource(R.string.app_name)
    val versionName = remember(context) {
        @Suppress("DEPRECATION")
        context.packageManager?.getPackageInfo(context.packageName, 0)?.versionName
    }

    Surface(modifier.fillMaxSize(), color = TickTheme.colorScheme.background) {
        LazyColumn(
            contentPadding = PaddingValues(TickTheme.sizes.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    versionName?.let { "$appName, v$it" } ?: appName,
                    textAlign = TextAlign.Center,
                    style = TickTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SettingsPreview() {
    TickTheme {
        Settings()
    }
}
