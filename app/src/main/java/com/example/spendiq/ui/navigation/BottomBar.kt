package com.example.spendiq.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spendiq.ui.navigation.Screen
@Composable
fun BottomBar(
    navController: NavController
) {
    val items = listOf(
        Screen.Home,
        Screen.Stats,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    val icon =when (screen) {
                        Screen.Home -> Icons.Filled.Home
                        Screen.Stats -> Icons.Filled.ShowChart
                        Screen.Profile -> Icons.Filled.Person
                        Screen.Add -> null // required to satisfy exhaustiveness
                    }
                    if (icon != null) {
                        androidx.compose.material3.Icon(
                            imageVector = icon,
                            contentDescription = screen.title
                        )
                    }
                },
                label = {
                    Text(screen.title)
                }
            )
        }
    }
}
