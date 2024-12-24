package com.example.ktwitnesses.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

@Suppress("ConvertObjectToDataObject")
sealed class NavRoutes(
	val route: String,
	val image: ImageVector? = null,
	val title: String? = null
) {
	object Home : NavRoutes(
		route = "home_screen",
		image = Icons.Outlined.Home,
		title = "Home")
	object Favorite : NavRoutes(
		route = "favourite_screen",
		image = Icons.Outlined.FavoriteBorder,
		title = "Favorite")
	object Cart : NavRoutes(
		route = "cart_screen",
		image = Icons.Outlined.ShoppingCart,
		title = "Cart")
	data object Profile : NavRoutes(
		route = "profile_screen",
		image = Icons.Outlined.Person,
		title = "Profile")
	data object Order : NavRoutes(
		route = "checkout_screen",
		image = Icons.Outlined.ShoppingCart,
		title = "Order")
}

