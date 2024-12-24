package com.example.ktwitnesses.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuccessScreen(orderNumber: Int, onNavigateToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Заказ оформлен",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Ваш заказ №${orderNumber} успешно оплачен.",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onNavigateToHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "На главную", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
