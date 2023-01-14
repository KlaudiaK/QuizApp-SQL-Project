package com.android.quizzy.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = navyDarkBlue60,
    onPrimary = ecru,
    secondary = lightPink20,
    onSecondary = white20,
    secondaryContainer = brown40,
    onPrimaryContainer = ecru,
    primaryContainer = lightGreen20,
    tertiary = yellow20,
)

private val LightColorScheme = lightColorScheme(
    primary = darkGreen80,
    onPrimary = darkBrown80,
    primaryContainer = pastelBlue,
    onPrimaryContainer = pastelBlack,
    secondary = lightPink20,
    onSecondary = pastelBlack,
    secondaryContainer = beige20,
    background = Color.White,
    surface = lightGray,
    tertiary = lightPink60,
)

@Composable
fun QuizzyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = QuizzyTypography,
        content = content
    )
}