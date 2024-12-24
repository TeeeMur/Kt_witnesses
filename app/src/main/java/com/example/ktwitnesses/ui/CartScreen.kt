package com.example.ktwitnesses.ui

import CartViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ktwitnesses.CheckoutViewModel
import com.example.ktwitnesses.R
import com.example.ktwitnesses.data.Book

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel(),
               checkoutViewModel: CheckoutViewModel = viewModel(),
               navToCheckout: () -> Unit) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    if (cartItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Ваша корзина пуста")
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cartItems.keys.toList()) { book ->
                    CartItem(
                        book = book,
                        quantity = cartItems[book] ?: 0,
                        onRemove =
                        {
                            checkoutViewModel.setQuantityToCheckoutCart(book, cartItems[book] ?: 0)
                            cartViewModel.removeFromCart(book)
                        },
                        onAdd =
                        {
                            cartViewModel.addToCart(book)
                            checkoutViewModel.setQuantityToCheckoutCart(book, cartItems[book] ?: 0)
                        },
                        isChecked = checkoutViewModel.checkoutCartItems.value[book.id]?.isCheckout ?: false,
                        onCheckboxChange = { checkoutViewModel.toggleCheckbox(book, cartItems[book] ?: 0) }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colors.surface)
            ) {
                Text(
                    text = "Итого: ${checkoutViewModel.totalPrice.collectAsState().value} Руб.",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Button(
                    onClick = { navToCheckout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Оформить заказ", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun CartItem(
    book: Book,
    quantity: Int,
    onRemove: () -> Unit,
    onAdd: () -> Unit,
    isChecked: Boolean,
    onCheckboxChange: (Boolean) -> Unit)
{
    var isCheckedCart by remember { mutableStateOf(isChecked) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(0.5f)
                    .height(200.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageLink?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_book_96),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.content_description),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    ) {
                    Text(
                        text = book.title.toString(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Checkbox(
                        checked = isCheckedCart,
                        onCheckedChange = { value: Boolean ->
                            isCheckedCart = value
                            onCheckboxChange(value)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Text(
                    text = "Автор: ${book.authors}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Цена: ${book.price} Руб.",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onRemove,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("-")
                    }

                    Text(
                        text = "$quantity",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )

                    Button(
                        onClick = onAdd,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("+")
                    }
                }
            }
        }
    }
}