package com.example.ktwitnesses

import androidx.lifecycle.ViewModel

@Suppress("ConvertObjectToDataObject")
sealed interface BooksUiState {
	data class Success(val bookSearch: List<Book>) : BooksUiState
	object Error : BooksUiState
	object Loading : BooksUiState
}

class HomeViewModel(
	private val bookRepo: BooksRepository
): ViewModel() {
	var booksUiState:
}