package com.example.ktwitnesses.ui.bookScreen

import CartViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ktwitnesses.BooksUiState
import com.example.ktwitnesses.HomeViewModel
import com.example.ktwitnesses.R
import com.example.ktwitnesses.ui.NavRoutes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookScreen(
	bookid: Int,
	onLike: () -> Unit,
	homeViewModel: HomeViewModel,
	navController: NavController,
	cartViewModel: CartViewModel,
) {
	val book = (homeViewModel.booksUiState as BooksUiState.Success).bookSearch[bookid]
	Scaffold(
		topBar = {
			Row() {
				IconButton(
					onClick = {
						if (navController.previousBackStackEntry != null) {
							navController.navigateUp()
						}
					},
					content = {
						Icon(
							imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
							contentDescription = "Back"
						)
					}
				)
			}
		},
		bottomBar = {
			Row(
				modifier = Modifier.padding(16.dp)
			) {
				val booksInCartCount = cartViewModel.cartItems.collectAsState()
				if (booksInCartCount.value.getOrDefault(book, 0) == 0) {
					Button(
						onClick = { cartViewModel.addToCart(book)},
						shape = RoundedCornerShape(16.dp),
						modifier = Modifier
							.fillMaxWidth()
							.height(56.dp),
						elevation = ButtonDefaults.elevation(
							defaultElevation = 0.dp
						),
						colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
						border = BorderStroke(2.dp, Color.Black)
					) {
						Row(
							horizontalArrangement = Arrangement.SpaceBetween
						) {
							Text(text = stringResource(R.string.add_to_cart) + " ")
							Text(text = book.price.toString() + stringResource(R.string.price_currency))
						}
					}
				}
				else {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						modifier = Modifier.fillMaxWidth()
					) {
						Button(
							onClick = {cartViewModel.removeFromCart(book)},
							shape = RoundedCornerShape(16.dp),
							modifier = Modifier
								.height(56.dp)
								.weight(0.16f),
							elevation = ButtonDefaults.elevation(
								defaultElevation = 0.dp
							),
							colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
							border = BorderStroke(2.dp, Color.Black)
						) {
							Text("-")
						}
						Text(
							modifier = Modifier.weight(0.16f),
							text = "${booksInCartCount.value.getOrDefault(book, 0)}",
							style = MaterialTheme.typography.body1,
							color = Color.Black,
							fontSize = 24.sp,
							textAlign = TextAlign.Center
						)
						Button(
							onClick = {cartViewModel.addToCart(book)},
							shape = RoundedCornerShape(16.dp),
							modifier = Modifier
								.height(56.dp)
								.weight(0.16f),
							elevation = ButtonDefaults.elevation(
								defaultElevation = 0.dp
							),
							colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
							border = BorderStroke(2.dp, Color.Black)
						) {
							Text("+")
						}
						Button(
							onClick = {navController.navigate(NavRoutes.Cart.route)},
							shape = RoundedCornerShape(16.dp),
							modifier = Modifier
								.height(56.dp)
								.weight(0.5f)
								.padding(start = 4.dp),
							elevation = ButtonDefaults.elevation(
								defaultElevation = 0.dp
							),
							colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
							border = BorderStroke(2.dp, Color.Black),
						) {
							Row (
								modifier = Modifier.fillMaxWidth()
									.padding(horizontal = 4.dp),
								horizontalArrangement = Arrangement.SpaceBetween,
								verticalAlignment = Alignment.CenterVertically
							) {
								Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "")
								Text("Корзина")
							}
						}
					}
				}
			}
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.padding(paddingValues)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			AsyncImage(
				modifier = Modifier
					.requiredHeight(420.dp)
					.padding(vertical = 16.dp)
					.fillMaxWidth(0.8f)
					.clip(RoundedCornerShape(24.dp)),
				model = coil.request.ImageRequest.Builder(context = LocalContext.current)
					.data(book.imageLink?.replace("http", "https"))
					.crossfade(true)
					.build(),
				error = painterResource(id = R.drawable.ic_book_96),
				placeholder = painterResource(id = R.drawable.loading_img),
				contentDescription = stringResource(id = R.string.content_description),
				contentScale = ContentScale.Crop
			)
			Row(
				horizontalArrangement = Arrangement.SpaceBetween,
				modifier = Modifier.fillMaxWidth()
			) {
				book.title?.let{
					Text(text = book.title,
						fontSize = 24.sp,
						)
				}
				IconButton(
					onClick = {onLike()},
				) {
					Icon(
						imageVector = Icons.Outlined.FavoriteBorder,
						contentDescription = "Like"
					)
				}
			}
			book.authors?.let{
				Text(text = book.authors[0], modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
			}
			book.description?.let{
				Text(text = book.description)
			}
			book.genre?.let{
				FlowRow(
					maxItemsInEachRow = 3
				) {
					book.genre.forEach {
						Text(text = it)
					}
				}
			}
		}
	}
}