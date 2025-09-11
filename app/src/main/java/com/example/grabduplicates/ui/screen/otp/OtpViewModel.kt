package com.example.grabduplicates.ui.screen.otp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OtpViewModel: ViewModel(){
    private val _otpValue = MutableStateFlow("")
    val otpValue: StateFlow<String> = _otpValue

    private val _isLoadingOtp = MutableStateFlow(false)
    val isLoadingOtp: StateFlow<Boolean> = _isLoadingOtp

    private val _isFailure = MutableStateFlow(false)
    val isFailure: StateFlow<Boolean> = _isFailure

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _showClearText = MutableStateFlow(false)
    val showClearText: StateFlow<Boolean> = _showClearText

    fun setOtpValue(value: String){
        _otpValue.value = value
        _showClearText.value = value != "" || value.isNotBlank()
    }

    fun clearOtp(){
        _otpValue.value = ""
    }

    fun setIsLoadingOtp(value: Boolean){
        _isLoadingOtp.value = value
    }

    fun setFailure(value: Boolean){
        _isFailure.value = value
    }

    fun setSuccess(value: Boolean){
        _isSuccess.value = value
    }
}