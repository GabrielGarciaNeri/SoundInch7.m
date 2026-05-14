package com.example.soundinch7.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = SoundInPrimaryDark,
    secondary = SoundInSecondaryDark,
    background = SoundInSecondaryDark,
    surface = SoundInSurfaceDark,
    onPrimary = SoundInOnPrimaryDark,
    onBackground = SoundInOnBackgroundDark,
    onSurface = SoundInOnSurfaceDark,
)

private val LightColorScheme = lightColorScheme(
    primary = SoundInPrimary,
    secondary = SoundInSecondary,
    background = SoundInBackground,
    surface = SoundInSurface,
    onPrimary = SoundInOnPrimary,
    onSecondary = SoundInOnSecondary,
    onBackground = SoundInOnBackground,
    onSurface = SoundInOnSurface,


)

@Composable
fun SoundInch7Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // use our own theme (False)
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(20.dp),
            extraLarge = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}