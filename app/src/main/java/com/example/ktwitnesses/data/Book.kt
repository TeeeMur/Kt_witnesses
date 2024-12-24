package com.example.ktwitnesses.data

data class Book(
	val authors: List<String>?,
	val title: String?,
	val previewLink: String?,
	val imageLink: String?,
	val price: Int = 500,
	val description: String?,
	val genres: List<String>?,
)