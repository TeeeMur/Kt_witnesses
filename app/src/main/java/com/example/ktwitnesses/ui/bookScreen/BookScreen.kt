package com.example.ktwitnesses.ui.bookScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ktwitnesses.BooksUiState
import com.example.ktwitnesses.HomeViewModel
import com.example.ktwitnesses.data.Book

@Composable
fun BookScreen(
	bookid: Int,
	onLike: () -> Unit,
	homeViewModel: HomeViewModel,
	navigateToCart: () -> Unit,
) {
	val book = (homeViewModel.booksUiState as BooksUiState.Success).bookSearch[bookid]
	Scaffold(
		topBar = {
			Row() {
				IconButton(
					onClick = {},
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
		Text(
			text = "laskdjf",
			modifier = Modifier.padding(paddingValues)
		)
	}
}