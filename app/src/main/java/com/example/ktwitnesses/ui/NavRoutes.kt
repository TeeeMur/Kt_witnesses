package com.example.ktwitnesses.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavRoutes(
	val route: String,
	val image: ImageVector? = null,
	val title: String? = null
) {
	data object Home : NavRoutes(
		route = "home_screen",
		image = Icons.Outlined.Home,
		title = "Home")
	data object Favorite : NavRoutes(
		route = "favourite_screen",
		image = Icons.Outlined.FavoriteBorder,
		title = "Favorite")
	data object Cart : NavRoutes(
		route = "cart_screen",
		image = Icons.Outlined.ShoppingCart,
		title = "Cart")
	data object Checkout : NavRoutes(
		route = "checkout_screen",
		title = "Checkout")
	data object Profile : NavRoutes(
		route = "profile_screen",
		image = Icons.Outlined.Person,
		title = "Profile")
	data object Order : NavRoutes(
		route = "checkout_screen",
		image = Icons.Outlined.ShoppingCart,
		title = "Order")
	data object BookCard : NavRoutes(
		route = "bookCard_screen",
		image = Icons.Outlined.Add,
		title = "Book")
}

