package com.example.spotsync.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

public val LightColorPalette = lightColors(
    primary = Color(
        0xA4 / 255f,
        0xAF / 255f,
        0x69 / 255f
    )
,    primaryVariant = Color(
        164, 175, 105
) ,


    secondary = Color(255, 224, 181) ,

    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

public val DarkColorPalette = darkColors(
    primary = Color(
        0xA4 / 255f,
        0xAF / 255f,
        0x69 / 255f
    )
    ,    primaryVariant = Color(
        164, 175, 105
    ) ,


    secondary = Color(255, 224, 181) ,
//     Other default colors to override
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,

)

@Composable
fun SpotSyncTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )

}