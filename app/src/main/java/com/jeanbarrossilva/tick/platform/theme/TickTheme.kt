package com.jeanbarrossilva.tick.platform.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.tick.platform.theme.extensions.Rubik
import com.jeanbarrossilva.tick.platform.theme.extensions.end
import com.jeanbarrossilva.tick.platform.theme.extensions.start
import com.jeanbarrossilva.tick.platform.theme.extensions.with
import com.jeanbarrossilva.tick.platform.theme.sizes.LocalSizes
import com.jeanbarrossilva.tick.platform.theme.sizes.Sizes

/** Height of [ColorSchemePreview]. **/
private const val COLOR_SCHEME_PREVIEW_HEIGHT = 1_843

/** Height of [ShapesPreview]. **/
private const val SHAPES_PREVIEW_HEIGHT = 898

/** Height of [TypographyPreview]. **/
private const val TYPOGRAPHY_PREVIEW_HEIGHT = 1_100

/** [ColorScheme] for when the system theme is dark. **/
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8DD88C),
    onPrimary = Color(0xFF00390D),
    primaryContainer = Color(0xFF005317),
    onPrimaryContainer = Color(0xFFA8F5A5),
    inversePrimary = Color(0xFF246C2D),
    secondary = Color(0xFFB9CCB4),
    onSecondary = Color(0xFF253423),
    secondaryContainer = Color(0xFF3B4B39),
    onSecondaryContainer = Color(0xFFD5E8CF),
    tertiary = Color(0xFFAED36E),
    onTertiary = Color(0xFF233600),
    tertiaryContainer = Color(0xFF354E00),
    onTertiaryContainer = Color(0xFFCAF087),
    background = Color(0xFF1A1C19),
    onBackground = Color(0xFFE2E3DD),
    surface = Color(0xFF1A1C19),
    onSurface = Color(0xFFE2E3DD),
    surfaceVariant = Color(0xFF424940),
    onSurfaceVariant = Color(0xFFC2C9BD),
    surfaceTint = Color(0xFF636D60),
    inverseSurface = Color(0xFFE2E3DD),
    inverseOnSurface = Color(0xFF1A1C19),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    outline = Color(0xFF343832),
    outlineVariant = Color(0xFF424940),
    scrim = Color(0xFF000000)
)

/** [ColorScheme] for when the system theme is light. **/
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF246C2D),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFA8F5A5),
    onPrimaryContainer = Color(0xFF002105),
    inversePrimary = Color(0xFF8DD88C),
    secondary = Color(0xFF52634F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD5E8CF),
    onSecondaryContainer = Color(0xFF101F10),
    tertiary = Color(0xFF49670C),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFCAF087),
    onTertiaryContainer = Color(0xFF131F00),
    background = Color(0xFFFCFDF6),
    onBackground = Color(0xFF1A1C19),
    surface = Color(0xFFFCFDF6),
    onSurface = Color(0xFF1A1C19),
    surfaceVariant = Color(0xFFE8ECE4),
    onSurfaceVariant = Color(0xFF424940),
    surfaceTint = Color(0xFFCED8C7),
    inverseSurface = Color(0xFF2F312D),
    inverseOnSurface = Color(0xFFF0F1EB),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    outline = Color(0xFFF2F5E4),
    outlineVariant = Color(0xFFC2C9BD),
    scrim = Color(0xFF000000)
)

/** Provider of [TickTheme]'s configurations. **/
internal object TickTheme {
    /** Current [ColorScheme] from the underlying [MaterialTheme]. **/
    val colorScheme
        @Composable get() = MaterialTheme.colorScheme

    /**
     * [Icons][androidx.compose.material.icons.Icons] in the chosen style. Alias for
     * [Icons.Rounded].
     **/
    val Icons = androidx.compose.material.icons.Icons.Rounded

    /** Current [Shapes] from the underlying [MaterialTheme]. **/
    val shapes
        @Composable get() = MaterialTheme.shapes

    /** Current [Sizes] from [LocalSizes]. **/
    val sizes
        @Composable get() = LocalSizes.current

    /** Current [Typography] from the underlying [MaterialTheme]. **/
    val typography
        @Composable get() = MaterialTheme.typography
}

/**
 * [MaterialTheme] for Academy.
 *
 * @param content Content to be themed.
 **/
@Composable
internal fun TickTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme,
        typography = with(Typography() with FontFamily.Rubik) {
            copy(titleLarge = titleLarge.copy(fontWeight = FontWeight.Bold))
        }
    ) {
        CompositionLocalProvider(
            LocalSizes provides Sizes.default,
            LocalTextStyle provides TickTheme.typography.bodyMedium,
            content = content
        )
    }
}

/** Preview of [TickTheme]'s [ColorScheme]. **/
@Composable
@Preview(heightDp = COLOR_SCHEME_PREVIEW_HEIGHT)
@Preview(heightDp = COLOR_SCHEME_PREVIEW_HEIGHT, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ColorSchemePreview() {
    TickTheme {
        Column(Modifier.fillMaxWidth()) {
            ColorSchemeSection("Primary") {
                Color(TickTheme.colorScheme.primary)
                Color(TickTheme.colorScheme.inversePrimary)
                Color(TickTheme.colorScheme.onPrimary)
            }

            ColorSchemeSection("Primary container") {
                Color(TickTheme.colorScheme.primaryContainer)
                Color(TickTheme.colorScheme.onPrimaryContainer)
            }

            ColorSchemeSection("Secondary") {
                Color(TickTheme.colorScheme.secondary)
                Color(TickTheme.colorScheme.onSecondary)
            }

            ColorSchemeSection("Secondary container") {
                Color(TickTheme.colorScheme.secondaryContainer)
                Color(TickTheme.colorScheme.onSecondaryContainer)
            }

            ColorSchemeSection("Tertiary") {
                Color(TickTheme.colorScheme.tertiary)
                Color(TickTheme.colorScheme.onTertiary)
            }

            ColorSchemeSection("Tertiary container") {
                Color(TickTheme.colorScheme.tertiaryContainer)
                Color(TickTheme.colorScheme.onTertiaryContainer)
            }

            ColorSchemeSection("Background") {
                Color(TickTheme.colorScheme.background)
                Color(TickTheme.colorScheme.onBackground)
            }

            ColorSchemeSection("Surface") {
                Color(TickTheme.colorScheme.surface)
                Color(TickTheme.colorScheme.inverseSurface)
                Color(TickTheme.colorScheme.onSurface)
                Color(TickTheme.colorScheme.inverseOnSurface)
                Color(TickTheme.colorScheme.surfaceTint)
            }

            ColorSchemeSection("Surface variant") {
                Color(TickTheme.colorScheme.surfaceVariant)
                Color(TickTheme.colorScheme.onSurfaceVariant)
            }

            ColorSchemeSection("Error") {
                Color(TickTheme.colorScheme.error)
                Color(TickTheme.colorScheme.onError)
            }

            ColorSchemeSection("Error container") {
                Color(TickTheme.colorScheme.errorContainer)
                Color(TickTheme.colorScheme.onErrorContainer)
            }

            ColorSchemeSection("Outline") {
                Color(TickTheme.colorScheme.outline)
            }

            ColorSchemeSection("Outline variant") {
                Color(TickTheme.colorScheme.outlineVariant)
            }

            ColorSchemeSection("Scrim") {
                Color(TickTheme.colorScheme.scrim)
            }
        }
    }
}

/** Preview of [TickTheme]'s [Shapes]. **/
@Composable
@Preview(heightDp = SHAPES_PREVIEW_HEIGHT)
@Preview(heightDp = SHAPES_PREVIEW_HEIGHT, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ShapesPreview() {
    TickTheme {
        Surface(Modifier.fillMaxWidth(), color = TickTheme.colorScheme.background) {
            Column {
                ShapeSection("Extra large", TickTheme.shapes.extraLarge)
                ShapeSection("Large", TickTheme.shapes.large)
                ShapeSection("Medium", TickTheme.shapes.medium)
                ShapeSection("Small", TickTheme.shapes.small)
                ShapeSection("Extra small", TickTheme.shapes.extraSmall)
            }
        }
    }
}

/** Preview of [TickTheme]'s [Sizes]. **/
@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SizesPreview() {
    TickTheme {
        Surface(Modifier.fillMaxWidth()) {
            Column {
                SizeSection("Extra large", TickTheme.sizes.extraLarge)
                SizeSection("Large", TickTheme.sizes.large)
                SizeSection("Medium", TickTheme.sizes.medium)
                SizeSection("Small", TickTheme.sizes.small)
                SizeSection("Extra small", TickTheme.sizes.extraSmall)
            }
        }
    }
}

/** Preview of [TickTheme]'s [Typography]. **/
@Composable
@Preview(heightDp = TYPOGRAPHY_PREVIEW_HEIGHT)
@Preview(heightDp = TYPOGRAPHY_PREVIEW_HEIGHT, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TypographyPreview() {
    TickTheme {
        Surface(Modifier.fillMaxWidth(), color = TickTheme.colorScheme.background) {
            Column {
                TypographySection("Display") {
                    Text("D1", style = TickTheme.typography.displayLarge)
                    Text("D2", style = TickTheme.typography.displayMedium)
                    Text("D3", style = TickTheme.typography.displaySmall)
                }

                TypographySection("Headline") {
                    Text("H1", style = TickTheme.typography.headlineLarge)
                    Text("H2", style = TickTheme.typography.headlineMedium)
                    Text("H3", style = TickTheme.typography.headlineSmall)
                }

                TypographySection("Title") {
                    Text("T1", style = TickTheme.typography.titleLarge)
                    Text("T2", style = TickTheme.typography.titleMedium)
                    Text("T3", style = TickTheme.typography.titleSmall)
                }

                TypographySection("Body") {
                    Text("B1", style = TickTheme.typography.bodyLarge)
                    Text("B2", style = TickTheme.typography.bodyMedium)
                    Text("B3", style = TickTheme.typography.bodySmall)
                }

                TypographySection("Label") {
                    Text("L1", style = TickTheme.typography.labelLarge)
                    Text("L2", style = TickTheme.typography.labelMedium)
                    Text("L3", style = TickTheme.typography.labelSmall)
                }
            }
        }
    }
}

/**
 * [Section] that displays the [Color][com.jeanbarrossilva.tick.platform.theme.Color]s provided in
 * the [content].
 *
 * @param title Text to be shown in the header, that explains what's being displayed.
 * @param modifier [Modifier] to be applied to the underlying [Section].
 * @param content [Color][com.jeanbarrossilva.tick.platform.theme.Color]s to be shown.
 **/
@Composable
private fun ColorSchemeSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Section(title, modifier) {
        Row(content = content)
    }
}

/**
 * [Box] that displays the given [color].
 *
 * @param color [Color] to be displayed.
 **/
@Composable
private fun Color(color: Color) {
    Box(
        Modifier
            .background(color)
            .size(64.dp)
    )
}

/**
 * [Section] that displays the given [shape].
 *
 * @param title Text to be shown in the header, that explains what's being displayed.
 * @param shape [Shape] to be displayed.
 * @param modifier [Modifier] to be applied to the underlying [Section].
 **/
@Composable
private fun ShapeSection(title: String, shape: Shape, modifier: Modifier = Modifier) {
    Section(title, modifier) {
        Box(
            Modifier
                .padding(it)
                .clip(shape)
                .background(TickTheme.colorScheme.primaryContainer)
                .height(64.dp)
                .fillMaxWidth()
        )
    }
}

/**
 * [Section] that displays the given [size].
 *
 * @param title Text to be shown in the header, that explains what's being displayed.
 * @param size Size in [Dp]s to be displayed.
 * @param modifier [Modifier] to be applied to the underlying [Section].
 **/
@Composable
private fun SizeSection(title: String, size: Dp, modifier: Modifier = Modifier) {
    Section(title, modifier) { padding ->
        Text(
            "$size",
            Modifier.padding(start = padding.start, top = size, end = padding.end, bottom = size),
            style = TickTheme.typography.titleMedium
        )
    }
}

/**
 * [Section] that displays the [Text]s in the given [content].
 *
 * @param title Text to be shown in the header, that explains what's being displayed.
 * @param modifier [Modifier] to be applied to the underlying [Section].
 * @param content [Text]s to be shown.
 **/
@Composable
private fun TypographySection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Section(title, modifier) {
        Column(
            modifier.padding(it),
            Arrangement.spacedBy(TickTheme.sizes.small),
            content = content
        )
    }
}

/**
 * Displays a header with the given [title] followed by the [content].
 *
 * @param title Text to be shown in the header, that explains what's being displayed.
 * @param modifier [Modifier] to be applied to the underlying [Column].
 * @param content Content to be displayed. Receives the same padding that's applied to the header.
 **/
@Composable
private fun Section(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(padding: PaddingValues) -> Unit
) {
    val padding = PaddingValues(TickTheme.sizes.large)

    Column(modifier) {
        Text(
            title,
            Modifier
                .background(TickTheme.colorScheme.surfaceVariant)
                .padding(padding)
                .fillMaxWidth(),
            TickTheme.colorScheme.onSurfaceVariant,
            style = TickTheme.typography.titleMedium
        )

        content(padding)
    }
}
