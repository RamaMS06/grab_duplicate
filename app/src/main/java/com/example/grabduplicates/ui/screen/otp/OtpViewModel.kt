package com.example.grabduplicates.ui.screen.otp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OtpViewModel: ViewModel(){
    private var _otpValue = MutableStateFlow("")
    var otpValue: StateFlow<String> = _otpValue

    fun setOtpValue(value: String){
        _otpValue.value = value
    }

    fun clearOtp(){
        _otpValue.value = ""
    }
}