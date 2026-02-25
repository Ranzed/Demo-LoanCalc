package com.ranzed.testloancalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = DarkMainBg,
    surface = DarkMainBg,
    primary = ButtonGradientStart,
    onPrimary = ButtonText,
    onBackground = DarkValueText,
    onSurface = DarkValueText,
    outline = DarkBorder,
    surfaceVariant = DarkPromoBg,
    onSurfaceVariant = DarkLabelText
)

private val LightColorScheme = lightColorScheme(
    background = LightMainBg,
    surface = LightMainBg,
    primary = LightLabelText,
    onPrimary = LightMainBg,
    onBackground = LightValueText,
    onSurface = LightValueText,
    outline = LightPromoBorder,
    surfaceVariant = LightPromoBg,
    onSurfaceVariant = LightLabelText
)

@Composable
fun TestLoanCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}