package com.example.ktwitnesses.data

data class Book(
	val id: String,
	val title: String?,
	val authors: ArrayList<String>?,
	val price: Int,
	val previewLink: String?,
	val imageLink: String?,
	val description: String?,
	val genre: List<String>?,
)

data class BookCart(
	val book: Book,
	var quantity: Int,
	var isCheckout: Boolean,
) {
	constructor(book: Book, quantity: Int) : this(book, quantity, false)
}

