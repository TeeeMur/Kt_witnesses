package com.example.ktwitnesses.ui

import AddressViewModel
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ktwitnesses.HomeViewModel
import com.example.ktwitnesses.R

@Composable
fun MainScreen() {
	val navController = rememberNavController()
	val currentBackStackEntry = navController.currentBackStackEntryAsState().value
	val currentRoute = currentBackStackEntry?.destination?.route
	val addressViewModel: AddressViewModel = viewModel()
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
			composable(NavRoutes.Cart.route) {
				Scaffold(
					modifier = Modifier.fillMaxSize(),
					topBar = {
						TopAppBar(
							title = {
								Text(text = stringResource(id = R.string.nav_routes_cart))
							}
						)
					}

				) {
					Surface(
						modifier = Modifier
							.fillMaxSize()
							.padding(it),
						color = MaterialTheme.colors.background
					) {
						CartScreen(cartViewModel, checkoutViewModel, {navController.navigate(NavRoutes.Checkout.route)})
					}
				}
			}
			composable(NavRoutes.Order.route) {
				OrderScreen(
					navController = navController,
					onBackPressed = { navController.popBackStack() }
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