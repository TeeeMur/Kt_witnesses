@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.ktwitnesses.ui

import AddressViewModel
import OrderViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview(showBackground = true)
@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModel1: AddressViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackPressed: () -> Unit,
    onChangeAddress: () -> Unit = {},
    onUserClick: () -> Unit = {},
    onAddressChange: () -> Unit = {},
    onCommentClick: () -> Unit = {}
) {
    val selectedOption by viewModel.selectedOption
    val isChecked by viewModel.isChecked
    val address by viewModel1.selectedAddress.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = { Text("Оформление заказа") }, navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Самовывоз", "Курьер").forEach { option ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                            .selectable(selected = (option == selectedOption),
                                onClick = { viewModel.setSelectedOption(option) })
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (option == selectedOption) Color.Green else Color.Transparent
                                )
                                .border(1.dp, Color.Black, RoundedCornerShape(50))
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = option, fontWeight = FontWeight.Bold, fontSize = 16.sp
                        )
                    }
                }
            }
            if (selectedOption == "Самовывоз") {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Пункт выдачи", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Адрес: $address", modifier = Modifier.padding(vertical = 4.dp))
                    Text("Срок хранения заказа: 14 дней", modifier = Modifier.padding(bottom = 16.dp))
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onUserClick)
                    ) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Пользователь")
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Иван Иванов")
                            Text("Тел: 8 800 555 35 35")
                        }
                    }
                    Button(
                        onClick = { navController.navigate("address_selection")},
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Изменить адрес") }
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Курьер")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Курьер по адресу", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Text("Адрес: г. Москва, ул. Курьерская, 15", modifier = Modifier.padding(vertical = 4.dp))
                    Button(
                        onClick = onAddressChange,
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Изменить адрес") }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onUserClick)
                    ) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Пользователь")
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Иван Иванов")
                            Text("Тел: 8 800 555 35 35")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onCommentClick)
                    ) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Комментарии")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Комментарии курьеру")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.toggleChecked() }
                            .padding(vertical = 8.dp)
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { viewModel.toggleChecked() }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Оставить у двери")
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Как это работает?",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable { }
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Логика оплаты */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Оформить заказ", color = Color.Black, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
