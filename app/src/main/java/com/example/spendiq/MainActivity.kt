package com.example.spendiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.spendiq.ui.navigation.BottomBar
import com.example.spendiq.ui.navigation.NavGraph
import com.example.spendiq.ui.theme.SpendIQTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendIQTheme {
                SpendIQApp()
            }
        }
    }
}

@Composable
fun SpendIQApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
