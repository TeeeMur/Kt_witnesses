package com.example.ktwitnesses.network

import com.example.ktwitnesses.network.model.BookShelf
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

	@GET("volumes")
	suspend fun bookSearch(
		@Query("q") searchQuery: String,
		@Query("maxResults") maxResults: Int
	): BookShelf
}