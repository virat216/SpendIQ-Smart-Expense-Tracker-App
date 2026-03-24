package com.example.spendiq.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Add : BottomNavItem("add", "Add", Icons.Default.Add)
    object Stats : BottomNavItem("stats", "Stats", Icons.Default.PieChart)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}
