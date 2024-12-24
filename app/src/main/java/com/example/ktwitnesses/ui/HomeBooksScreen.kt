package com.example.ktwitnesses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ktwitnesses.BooksUiState
import com.example.ktwitnesses.CartViewModel
import com.example.ktwitnesses.R
import com.example.ktwitnesses.data.Book
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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

@Suppress("NonSkippableComposable")
@Composable
fun BooksCard(
    book: Book,
    modifier: Modifier,
    cartViewModel: CartViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

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
            Row {
                Text(
                    text = book.price.toString() + stringResource(R.string.price_currency),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .padding(top = 4.dp, bottom = 6.dp),
                )
                Button(
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    onClick = { coroutineScope.launch { cartViewModel.addToCart(book); }}) {
                }
            }
        }
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun BooksGridScreen(
    books: List<Book>,
    modifier: Modifier,
    onMaxScroll: () -> Unit,
    cartViewModel: CartViewModel = viewModel()
) {
    val lazyVerticalGridState = rememberLazyGridState()
    val firstVisibleItemIndex by remember { derivedStateOf { lazyVerticalGridState.firstVisibleItemIndex } }
    val totalItemsCount by remember { derivedStateOf { lazyVerticalGridState.layoutInfo.totalItemsCount } }
    if (firstVisibleItemIndex > (totalItemsCount / 3)) {
        onMaxScroll()
    }
    LazyVerticalGrid(
        state = lazyVerticalGridState,
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
    cartViewModel: CartViewModel = viewModel()
) {
    when (booksUiState) {
        is BooksUiState.Loading -> LoadingScreen(modifier)
        is BooksUiState.Success -> {
            Column() {
                BooksGridScreen(
                    books = booksUiState.bookSearch,
                    modifier = modifier,
                    onMaxScroll = onMaxScroll,
                    cartViewModel = cartViewModel
                )
            }
        }

        is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
    }
}

@Composable
fun FavouriteScreen() {
    Text("Favourite")
}

@Composable
fun CartScreen(
    onProceedToOrder: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Корзина") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onProceedToOrder() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface
                )
            ) {
                Text(
                    text = "Оформить заказ",
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Text("Profile")
}
