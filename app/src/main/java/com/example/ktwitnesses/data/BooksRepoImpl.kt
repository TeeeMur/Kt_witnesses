package com.example.ktwitnesses.data

import com.example.ktwitnesses.network.BookService
import kotlin.random.Random

class BooksRepoImpl (
	private val bookService: BookService
) : BooksRepository {
	override suspend fun getBooks(
		query: String,
		maxResults: Int
	): List<Book> = bookService.bookSearch(query, maxResults).items.map { items ->
		Book(
			authors = items.volumeInfo?.authors,
			title = items.volumeInfo?.title,
			previewLink = items.volumeInfo?.previewLink,
			imageLink = items.volumeInfo?.imageLinks?.thumbnail,
			genre = items.volumeInfo?.categories,
			description = items.volumeInfo?.description
			id = items.id.toString(),
			title = items.volumeInfo?.title,
			previewLink = items.volumeInfo?.previewLink,
			authors = items.volumeInfo?.authors,
			imageLink = items.volumeInfo?.imageLinks?.thumbnail,
			price = Random.nextInt(10, 10000)
		)
	}
}