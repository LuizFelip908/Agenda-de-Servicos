package com.example.agendasdeservios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.agendasdeservios.ui.navigation.AppNavigation
import com.example.agendasdeservios.ui.theme.AgendasDeServiçosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendasDeServiçosTheme {
                AppNavigation()
            }
        }
    }
}
