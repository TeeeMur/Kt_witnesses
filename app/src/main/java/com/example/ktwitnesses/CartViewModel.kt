package com.example.ktwitnesses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktwitnesses.data.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<Map<Book, Int>>(emptyMap())
    val cartItems: StateFlow<Map<Book, Int>> = _cartItems

    suspend fun addToCart(book: Book) {
        val updatedCart = _cartItems.value.toMutableMap()
        val existingQuantity = updatedCart[book] ?: 0
        updatedCart[book] = existingQuantity + 1
        _cartItems.value = updatedCart
    }

    suspend fun removeFromCart(book: Book) {
        val updatedCart = _cartItems.value.toMutableMap()
        val existingQuantity = updatedCart[book] ?: 0
        if (existingQuantity - 1 <= 0) {
            updatedCart.remove(book)
        } else {
            updatedCart[book] = existingQuantity - 1
        }
        _cartItems.value = updatedCart
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyMap()
        }
    }
}