package com.example.ktwitnesses.ui

import AddressViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ktwitnesses.BooksViewModel
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
                val booksViewModel: BooksViewModel =
                    viewModel(factory = BooksViewModel.Factory)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
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
                        HomeScreen(
                            booksUiState = booksViewModel.booksUiState,
                            retryAction = { booksViewModel.getBooks() },
                            modifier = Modifier
                        )
                    }
                }
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
            composable(NavRoutes.Success.route) {

            }
        }
    }
}