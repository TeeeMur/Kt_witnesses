package com.example.ktwitnesses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
	val navController = rememberNavController()
	Column(Modifier.padding(8.dp)) {
		NavHost(navController, startDestination = NavRoutes.Home.route, modifier = Modifier.weight(1f)) {
			composable(NavRoutes.Home.route) { HomeScreen() }
			composable(NavRoutes.Favorite.route) { FavouriteScreen()  }
			composable(NavRoutes.Cart.route) { CartScreen() }
			composable(NavRoutes.Profile.route) { ProfileScreen() }
		}
		BottomNavigationBar(navController = navController)
	}
}