package com.example.soundinch7.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    // // State is Private only Visible to this class ViewModel
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _rememberSession = MutableStateFlow(false)
    private val _emailError = MutableStateFlow(false)
    private val _passwordError = MutableStateFlow(false)

    // public state is exposed to the UI
    val email: StateFlow<String> = _email.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val rememberSession: StateFlow<Boolean> = _rememberSession.asStateFlow()
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()

    // Update Functions update the state
    fun onEmailChanged(value: String) {
        _email.value = value;
        _emailError.value = false
    }

    fun onPasswordChanged(value: String) {
        _password.value = value;
        _passwordError.value = false
    }

    fun onRememberSessionChanged(value: Boolean) {
        _rememberSession.value = value;
    }

    // Validation Login  Logic
    fun validateAndLogin(): Boolean {
        val isEmailValid = _email.value.contains("@") && _email.value.contains(".")
        val isPasswordValid = _password.value.length >= 8

        _emailError.value = !isEmailValid
        _passwordError.value = !isPasswordValid

        return isPasswordValid && isEmailValid
    }
}