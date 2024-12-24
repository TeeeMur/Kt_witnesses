package com.example.ktwitnesses.ui

import OrderViewModel
import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(navController: NavController) {
	NavigationBar (
		containerColor = Color.White
	){
		val backStackEntry by navController.currentBackStackEntryAsState()
		val currentRoute = backStackEntry?.destination?.route
		val screenItems = listOf(
			NavRoutes.Home, NavRoutes.Favorite, NavRoutes.Cart, NavRoutes.Profile
		)
		screenItems.forEach {
			NavigationBarItem(
				selected = currentRoute == it.route,
				onClick = {
					navController.navigate(it.route) {
						popUpTo(navController.graph.findStartDestination().id) { saveState = true }
						launchSingleTop = true
						restoreState = true
					}
				},
				icon = {
					Icon(
						imageVector = it.image!!,
						contentDescription = it.title
					)
				},
				label = {
					Text(text = it.title!!)
				},
				alwaysShowLabel = false,
			)
		}
	}
}