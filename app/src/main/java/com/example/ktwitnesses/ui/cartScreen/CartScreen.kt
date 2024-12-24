package com.example.ktwitnesses.ui.cartScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

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