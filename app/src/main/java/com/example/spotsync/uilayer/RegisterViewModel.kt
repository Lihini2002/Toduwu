package com.example.spotsync.uilayer

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotsync.datalayer.AuthRepository
import com.example.spotsync.sharedlayer.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val isError: String? = null
)

@HiltViewModel
class UserSignupViewModel @Inject constructor(
    application: Application,
    private val repository: AuthRepository,
    private val context: Context
) : AndroidViewModel(application) {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState

    private val _loginState = MutableStateFlow(RegisterState())
    val loginState: StateFlow<RegisterState> = _loginState

    // Shared Preferences keys
    private val PREFS_NAME = "com.example.spotsync.prefs"
    private val PREF_KEY_IS_LOGGED_IN = "is_logged_in"

    // Example function to save authentication state to Shared Preferences
    private fun saveAuthState(isLoggedIn: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putBoolean(PREF_KEY_IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    // Example function to retrieve authentication state from Shared Preferences
    private fun getAuthState(): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(PREF_KEY_IS_LOGGED_IN, false)
    }

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collectLatest { result ->
            when (result) {
                is Resource.Loading -> {
                    _registerState.value = RegisterState(isLoading = true)
                }
                is Resource.Success -> {
                    _registerState.value = RegisterState(isSuccess = "Register Success")
                    saveAuthState(true) // Save login state to shared preferences on success
                }
                is Resource.Error -> {
                    _registerState.value = RegisterState(isError = "Error message: ${result.message}")
                }
            }
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collectLatest { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginState.value = RegisterState(isLoading = true)
                }
                is Resource.Success -> {
                    _loginState.value = RegisterState(isSuccess = "Login Success")
                    saveAuthState(true) // Save login state to shared preferences on success
                }
                is Resource.Error -> {
                    _loginState.value = RegisterState(isError = "Error message: ${result.message}")
                }
            }
        }
    }

    init {
        // Example: Check initial authentication state on ViewModel initialization
        if (getAuthState()) {
            // User is logged in, perform necessary actions
        } else {
            // User is not logged in, perform necessary actions
        }
    }
}

