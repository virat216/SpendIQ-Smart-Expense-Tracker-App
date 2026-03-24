package com.example.spendiq.ui.navigation

sealed class Screen(
    val route: String,
    val title: String
) {
    object Home : Screen("home", "Home")
    object Add : Screen("add", "Add")
    object Stats : Screen("stats", "Stats")
    object Profile : Screen("profile", "Profile")
}
