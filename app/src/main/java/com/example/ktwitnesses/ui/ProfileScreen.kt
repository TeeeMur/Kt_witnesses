package com.example.ktwitnesses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.LightGray, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Вася Пупкин",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Настройки профиля",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Menu items
            MenuItem(title = "Мои заказы") { /* Navigate to orders */ }
            MenuItem(title = "Отзывы") { /* Navigate to reviews */ }
            MenuItem(title = "Возвраты") { /* Navigate to returns */ }

            Spacer(modifier = Modifier.weight(1f))

            // Logout button
            Button(
                onClick = { /* Handle logout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Выйти из профиля")
            }
        }
    }
}

@Composable
fun MenuItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}
