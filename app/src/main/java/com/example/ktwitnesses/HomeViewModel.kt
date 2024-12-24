package com.example.ktwitnesses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ktwitnesses.data.BaseAppContainer
import com.example.ktwitnesses.data.Book
import com.example.ktwitnesses.data.BooksRepoImpl
import com.example.ktwitnesses.data.BooksRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BooksUiState {
	data class Success(val bookSearch: List<Book>) : BooksUiState
	data object Error : BooksUiState
	data object Loading : BooksUiState
}

class HomeViewModel(
	private val booksRepository: BooksRepository
) : ViewModel() {

	var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
		private set

	init {
		getBooks()
	}

	fun addBooks(query: String = "book", maxResults: Int = 40) {
		viewModelScope.launch {
			try {
				val addedBooksList = booksRepository.getBooks(query, maxResults)
				if (booksUiState is BooksUiState.Success) {
					val tempList = (booksUiState as BooksUiState.Success).bookSearch
					booksUiState = BooksUiState.Success(tempList + addedBooksList)
				}
			} catch (e: IOException) {
				booksUiState = BooksUiState.Error
			} catch (e: HttpException) {
				booksUiState = BooksUiState.Error
			}
		}
	}

	fun getBooks(query: String = "book", maxResults: Int = 40) {
		viewModelScope.launch {
			booksUiState = BooksUiState.Loading
			booksUiState =
				try {
					BooksUiState.Success(booksRepository.getBooks(query, maxResults))
				} catch (e: java.io.IOException) {
					BooksUiState.Error
				} catch (e: coil.network.HttpException) {
					BooksUiState.Error
				}
		}
	}

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val booksRepository = BooksRepoImpl(BaseAppContainer.retrofitService)
				HomeViewModel(booksRepository = booksRepository)
			}
		}
	}
}