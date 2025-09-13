package com.example.grabduplicates.ui.screen.phone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhoneViewModel: ViewModel() {

    private val _phoneValue = MutableStateFlow("")

    val phoneValue: StateFlow<String> = _phoneValue

    private val _isLoadingPhone = MutableStateFlow(false)

    val isLoadingPhone: StateFlow<Boolean> = _isLoadingPhone

    private val _isSuccess = MutableStateFlow(false)

    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun setPhoneNumber(value: String){
        _phoneValue.value = value
    }

    fun setLoadingPhone(value: Boolean){
        _isLoadingPhone.value = value
    }

    fun setSuccess(value: Boolean){
        _isSuccess.value = value
    }
}