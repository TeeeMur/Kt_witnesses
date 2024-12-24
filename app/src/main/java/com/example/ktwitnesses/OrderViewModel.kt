package com.example.ktwitnesses

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class OrderViewModel : ViewModel() {
    private val _selectedOption = mutableStateOf("Самовывоз")
    val selectedOption: State<String> = _selectedOption

    private val _isChecked = mutableStateOf(false)
    val isChecked: State<Boolean> = _isChecked

    fun setSelectedOption(option: String) {
        _selectedOption.value = option
    }

    fun toggleChecked() {
        _isChecked.value = !_isChecked.value
    }
}
