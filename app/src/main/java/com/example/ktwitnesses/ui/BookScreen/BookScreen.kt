package com.example.ktwitnesses.ui.BookScreen

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
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ktwitnesses.data.Book

@Composable
fun BookScreen(
	book: Book,
	onLike: () -> Unit,
	onAddToCart: (add: Boolean) -> Unit,
	navigateToCart: () -> Unit,
) {
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