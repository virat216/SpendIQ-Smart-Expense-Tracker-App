package com.example.spendiq.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spendiq.ui.screens.add.AddTransactionScreen
import com.example.spendiq.ui.screens.home.HomeScreen
import com.example.spendiq.ui.screens.profile.ProfileScreen
import com.example.spendiq.ui.screens.stats.StatsScreen
import androidx.compose.ui.Modifier

@Composable
fun NavGraph(navController: NavHostController,
             modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Add.route) {
            AddTransactionScreen(navController)
        }

        composable(Screen.Stats.route) {
            StatsScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
