package com.example.ktwitnesses.ui

import AddressViewModel
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ktwitnesses.HomeViewModel
import com.example.ktwitnesses.ui.bookScreen.BookScreen
import com.example.ktwitnesses.ui.cartScreen.AddressSelectionScreen
import com.example.ktwitnesses.ui.cartScreen.CartScreen
import com.example.ktwitnesses.ui.cartScreen.OrderScreen
import com.example.ktwitnesses.ui.homeScreen.BottomNavigationBar
import com.example.ktwitnesses.ui.homeScreen.FavouriteScreen
import com.example.ktwitnesses.ui.homeScreen.HomeBooksScreen
import com.example.ktwitnesses.ui.homeScreen.HomeScreen
import com.example.ktwitnesses.ui.homeScreen.ProfileScreen

@Composable
fun MainScreen() {
	val navController = rememberNavController()
	val currentBackStackEntry = navController.currentBackStackEntryAsState().value
	val currentRoute = currentBackStackEntry?.destination?.route
	val addressViewModel: AddressViewModel = viewModel()
	val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
	val screensWithBottomNav = listOf(
		NavRoutes.Home.route,
		NavRoutes.Favorite.route,
		NavRoutes.Cart.route,
		NavRoutes.Profile.route
	)
	Scaffold(
		bottomBar = {
			if (currentRoute in screensWithBottomNav) {
				BottomNavigationBar(navController = navController)
			}
		}

	)
	{ paddingValues ->
		NavHost(
			navController = navController,
			startDestination = NavRoutes.Home.route,
			modifier = Modifier.padding(paddingValues)
		) {
			composable(NavRoutes.Home.route) {
				HomeScreen(homeViewModel, navController)
			}
			composable(NavRoutes.Favorite.route) { FavouriteScreen() }
			composable(NavRoutes.Cart.route) {
				CartScreen(
					onProceedToOrder = { navController.navigate(NavRoutes.Order.route) }
				)
			}
			composable(NavRoutes.Order.route) {
				OrderScreen(
					navController = navController,
					onBackPressed = { navController.popBackStack() }
				)
			}
			composable("address_selection") {
				val addresses = addressViewModel.addresses.collectAsState().value
				val selectedAddress = addressViewModel.selectedAddress.collectAsState().value
				AddressSelectionScreen(
					addresses = addresses,
					selectedAddress = selectedAddress,
					onBackPressed = { navController.popBackStack() },
					onAddressSelected = { address ->
						addressViewModel.selectAddress(address)
					},
					onAddAddress = {
					},

					// попытка сохранить новый адрес (не увенчалась успехом)
					onSave = {
						selectedAddress?.let { address ->
							addressViewModel.selectAddress(address)
							navController.popBackStack()
						}
					},
					onBack = { navController.popBackStack() }
				)
			}
			composable(NavRoutes.Profile.route) { ProfileScreen() }
			composable(NavRoutes.BookCard.route + "/{bookid}") { stackEntry ->
				val bookid = stackEntry.arguments?.getInt("bookid")!!
				BookScreen(bookid = bookid, onLike = {}, homeViewModel = homeViewModel,
					navController = navController)
			}
		}
	}
}