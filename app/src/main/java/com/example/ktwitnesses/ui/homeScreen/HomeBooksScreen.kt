package com.example.ktwitnesses.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ktwitnesses.BooksUiState
import com.example.ktwitnesses.HomeViewModel
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
	modifier: Modifier
) {
	Card(
		modifier = modifier
			.padding(4.dp)
			.fillMaxWidth()
			.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
		elevation = 0.dp
	) {
		Column(
			modifier = Modifier.wrapContentHeight(unbounded = true),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			AsyncImage(
				modifier = modifier
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
			book.authors?.let { authorsList ->
				Text(
					text = authorsList.getOrElse(0) { "author" },
					textAlign = TextAlign.Start,
					modifier = modifier
						.padding(top = 4.dp)
						.fillMaxWidth(),
					maxLines = 1
				)
			}
			book.title?.let {
				Text(
					text = it,
					fontSize = 18.sp,
					textAlign = TextAlign.Start,
					modifier = modifier
						.padding(top = 4.dp, bottom = 4.dp)
						.fillMaxWidth(),
					maxLines = 1,
					softWrap = false,
				)
			}
			Text(
				text = book.price.toString() + stringResource(R.string.price_currency),
				fontSize = 18.sp,
				textAlign = TextAlign.Start,
				modifier = modifier
					.padding(top = 4.dp, bottom = 6.dp)
					.fillMaxWidth(),
			)
		}
	}
}

@Composable
fun BooksGridScreen(
	books: List<Book>,
	modifier: Modifier,
	onMaxScroll: () -> Unit
) {
	val lazyVerticalGridState = rememberLazyGridState()
	val firstVisibleItemIndex by remember { derivedStateOf { lazyVerticalGridState.firstVisibleItemIndex } }
	val totalItemsCount by remember { derivedStateOf { lazyVerticalGridState.layoutInfo.totalItemsCount } }
	if ( firstVisibleItemIndex > (totalItemsCount / 3)) {
		onMaxScroll()
	}
	LazyVerticalGrid(
		state = lazyVerticalGridState,
		columns = GridCells.Adaptive(150.dp),
		contentPadding = PaddingValues(6.dp),
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		itemsIndexed(books) { _, book ->
			BooksCard(book = book, modifier)
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
			contentDescription = stringResource(id = R.string.loading)
		)
	}
}

@Composable
fun HomeBooksScreen(
	booksUiState: BooksUiState,
	retryAction: () -> Unit,
	onMaxScroll: () -> Unit,
	modifier: Modifier,
) {
	when (booksUiState) {
		is BooksUiState.Loading -> LoadingScreen(modifier)
		is BooksUiState.Success -> {
			Column() {
				BooksGridScreen(
					books = booksUiState.bookSearch,
					modifier = modifier,
					onMaxScroll = onMaxScroll,
				)
			}
		}

		is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
	}
}

@Composable
fun HomeScreen() {
	val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
	var textFieldValue by remember { mutableStateOf("") }
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
				title = {},
				actions = {
					Row(
						modifier = Modifier.fillMaxWidth()
							.padding(end=12.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically,
					) {
						OutlinedTextField(
							modifier = Modifier
								.requiredWidth(300.dp)
								.border(2.dp, Color.Black,RoundedCornerShape(16.dp)),
							value = textFieldValue,
							onValueChange = { textFieldValue = it },
							placeholder = {
								androidx.compose.material.Text(
									text = stringResource(R.string.searchfield_placeholder),
									fontSize = 14.sp
								)
							},
							colors = TextFieldDefaults.outlinedTextFieldColors(
								focusedBorderColor = Color.Unspecified,
								unfocusedBorderColor = Color.Unspecified
							),
							singleLine = true
						)
						IconButton(
							onClick = {},
							modifier = Modifier
								.fillMaxHeight()
								.aspectRatio(1f)
								.border(
									2.dp,
									Color.Black,
									RoundedCornerShape(10.dp)
								),
						) {
							Icon(
								imageVector = Icons.AutoMirrored.Filled.List,
								contentDescription = "Сортировка/Фильтры",
							)
						}
					}
				},
				backgroundColor = Color.White,
				elevation = 0.dp,
			)
		}
	) {
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(it),
			color = MaterialTheme.colors.background
		) {
			HomeBooksScreen(
				booksUiState = homeViewModel.booksUiState,
				retryAction = { homeViewModel.getBooks() },
				modifier = Modifier,
				onMaxScroll = { homeViewModel.addBooks() }
			)
		}
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
