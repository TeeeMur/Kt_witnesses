package com.example.ktwitnesses.ui

import CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ktwitnesses.BooksUiState
import com.example.ktwitnesses.R
import com.example.ktwitnesses.data.Book

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = stringResource(id = R.string.loading_failed))
		Button(onClick = retryAction) {
			Text(text = stringResource(id = R.string.retry))
		}

	}
}

@Composable
fun BooksCard(
	book: Book,
	modifier: Modifier,
	cartViewModel: CartViewModel = viewModel()
) {
	Card(
		modifier = modifier
			.padding(4.dp)
			.fillMaxWidth()
			.requiredHeight(296.dp),
		elevation = 8.dp,
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			book.title?.let {
				Text(
					text = it,
					textAlign = TextAlign.Center,
					modifier = modifier
						.padding(top = 4.dp, bottom = 8.dp)
				)
			}
			AsyncImage(
				modifier = modifier.fillMaxWidth(),
				model = ImageRequest.Builder(context = LocalContext.current)
					.data(book.imageLink?.replace("http", "https"))
					.crossfade(true)
					.build(),
				error = painterResource(id = R.drawable.ic_book_96),
				placeholder = painterResource(id = R.drawable.loading_img),
				contentDescription = stringResource(id = R.string.content_description),
				contentScale = ContentScale.Crop
			)
			Button(
				modifier = modifier
				.padding(4.dp)
				.fillMaxWidth()
				.requiredHeight(296.dp),
				onClick = { cartViewModel.addToCart(book);}) {
			}
		}
	}
}

@Suppress("NonSkippableComposable")
@Composable
fun BooksGridScreen(
	books: List<Book>,
	modifier: Modifier,
	cartViewModel: CartViewModel = viewModel()
) {
	LazyVerticalGrid(
		columns = GridCells.Adaptive(150.dp),
		contentPadding = PaddingValues(4.dp)
	) {
		itemsIndexed(books) { _, book ->
			BooksCard(book = book, modifier, cartViewModel)
		}
	}
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		Image(
			modifier = Modifier.size(200.dp),
			painter = painterResource(id = R.drawable.loading_img),
			contentDescription = stringResource(id = R.string.loading))
	}
}

@Composable
fun HomeScreen(
	booksUiState: BooksUiState,
	retryAction: () -> Unit,
	modifier: Modifier,
	cartViewModel: CartViewModel = viewModel()
) {
	when (booksUiState) {
		is BooksUiState.Loading -> LoadingScreen(modifier)
		is BooksUiState.Success -> BooksGridScreen(
			books = booksUiState.bookSearch,
			modifier = modifier,
			cartViewModel = cartViewModel
		)
		is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
	}
}

@Composable
fun FavouriteScreen() {
	Text("Favourite")
}

@Composable
fun ProfileScreen() {
	Text("Profile")
}
