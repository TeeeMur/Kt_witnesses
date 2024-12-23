package com.example.ktwitnesses.data

interface BooksRepository {
	suspend fun getBooks(query: String, maxResults: Int) : List<Book>
}
