package com.example.grabduplicates.ui.screen.navbar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavBarViewModel: ViewModel() {
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    fun onPageChanged(page: Int){
        _currentPage.value = page
    }
}