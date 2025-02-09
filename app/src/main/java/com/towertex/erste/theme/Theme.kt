package com.towertex.erste.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TowertexErsteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = ErsteTypography,
        content = content
    )
}