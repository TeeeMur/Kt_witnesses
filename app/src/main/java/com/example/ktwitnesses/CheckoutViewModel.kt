package com.example.ktwitnesses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktwitnesses.data.Book
import com.example.ktwitnesses.data.BookCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {
    private val _checkoutCartItems = MutableStateFlow<Map<String, BookCart>>(emptyMap())
    private val _totalPrice = MutableStateFlow(0)

    val checkoutCartItems: StateFlow<Map<String, BookCart>> = _checkoutCartItems
    val totalPrice: StateFlow<Int> = _totalPrice


    fun toggleCheckbox(book: Book, quantity: Int) {
        viewModelScope.launch {
            val updatedCart = _checkoutCartItems.value.toMutableMap()
            val bookCart = updatedCart[book.id]
            if (bookCart != null) {
                updatedCart[book.id]?.isCheckout = !bookCart.isCheckout
                updatedCart[book.id]?.quantity = quantity
            }
            else{
                addToCheckoutCart(book, quantity)
            }
            _checkoutCartItems.value = updatedCart
            updateTotalPrice()
        }
    }

    private fun addToCheckoutCart(book: Book, quantity: Int) {
        viewModelScope.launch {
            val updatedCart = _checkoutCartItems.value.toMutableMap()
            updatedCart[book.id] = BookCart(book, quantity, true)
            _checkoutCartItems.value = updatedCart
        }
        updateTotalPrice()
    }

    fun setQuantityToCheckoutCart(book: Book, quantity: Int) {
        viewModelScope.launch {
            val updatedCart = _checkoutCartItems.value.toMutableMap()
            if (quantity <= 0){
                updatedCart.remove(book.id)
            }
            else{
                updatedCart[book.id]?.quantity = quantity
            }
            _checkoutCartItems.value = updatedCart
        }
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        viewModelScope.launch {
            var total = 0
            _checkoutCartItems.value.forEach { (id, bookCart) ->
                if (bookCart.isCheckout) {
                    total += bookCart.book.price * bookCart.quantity
                }
            }
            _totalPrice.value = total
        }
    }
}