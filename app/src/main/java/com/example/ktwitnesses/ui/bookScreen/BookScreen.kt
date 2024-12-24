package com.example.ktwitnesses.ui.bookScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ktwitnesses.BooksUiState
import com.example.ktwitnesses.HomeViewModel
import com.example.ktwitnesses.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookScreen(
	bookid: Int,
	onLike: () -> Unit,
	homeViewModel: HomeViewModel,
	navController: NavController
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
		}
	) { paddingValues ->
		Column(
			modifier = Modifier.padding(paddingValues)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			AsyncImage(
				modifier = Modifier
					.fillMaxWidth()
					.requiredHeight(240.dp)
					.clip(RoundedCornerShape(24.dp)),
				model = coil.request.ImageRequest.Builder(context = LocalContext.current)
					.data(book.imageLink?.replace("http", "https"))
					.crossfade(true)
					.build(),
				error = painterResource(id = R.drawable.ic_book_96),
				placeholder = painterResource(id = R.drawable.loading_img),
				contentDescription = stringResource(id = R.string.content_description),
				contentScale = ContentScale.FillWidth
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
				Text(text = book.authors[0])
			}
			book.description?.let{
				Text(text = book.description)
			}
			book.genres?.let{
				FlowRow(
					maxItemsInEachRow = 3
				) {
					book.genres.forEach {
						Text(text = it)
					}
				}
			}
		}
	}
}