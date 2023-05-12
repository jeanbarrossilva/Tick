package com.jeanbarrossilva.tick.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.jeanbarrossilva.tick.platform.theme.TickTheme

internal class TickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent()
    }

    private fun setContent() {
        setContent {
            TickTheme {
                Tick()
            }
        }
    }
}
