package com.towertex.erste.compose

import androidx.compose.runtime.Composable
import com.towertex.erste.navigation.ErsteNavHost
import com.towertex.erste.theme.TowertexErsteTheme
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun ErsteApp() {
    TowertexErsteTheme {
        KoinAndroidContext {
            ErsteNavHost()
        }
    }
}