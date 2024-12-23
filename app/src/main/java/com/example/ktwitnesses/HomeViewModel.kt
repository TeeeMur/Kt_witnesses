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
import okio.IOException
import retrofit2.HttpException

@Suppress("ConvertObjectToDataObject")
sealed interface BooksUiState {
	data class Success(val bookSearch: List<Book>) : BooksUiState
	object Error : BooksUiState
	object Loading : BooksUiState
}

class BooksViewModel(
	private val booksRepository: BooksRepository
): ViewModel() {
	var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
		private set


	init {
		getBooks()
	}

	fun getBooks(query: String = "book", maxResults: Int = 40) {
		viewModelScope.launch {
			booksUiState = BooksUiState.Loading
			booksUiState =
				try {
					BooksUiState.Success(booksRepository.getBooks(query, maxResults))
				} catch (e: IOException) {
					BooksUiState.Error
				} catch (e: HttpException) {
					BooksUiState.Error
				}
		}
	}

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val booksRepository = BooksRepoImpl(BaseAppContainer.retrofitService)
				BooksViewModel(booksRepository = booksRepository)
			}
		}
	}
}