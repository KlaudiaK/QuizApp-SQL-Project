package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.presentation.login.LoginScreenEvent
import com.android.quizzy.presentation.login.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginScreenState())
    val uiState: State<LoginScreenState> = _uiState


    private fun checkUserEmail(email: String) {
        viewModelScope.launch {
            if (email.isEmpty()) {
                _uiState.value = _uiState.value.copy(incorrectEmail = true)
            }
        }
    }

    private fun checkUserPassword(email: String, password: String) {
        viewModelScope.launch {
            if (password.isEmpty()) {
                _uiState.value = _uiState.value.copy(incorrectPassword = true)
            }
        }
    }

    private fun getUserId(username: String, password: String) {
        var userId: Int = -1
        runBlocking {
            userId = userRepository.login(username, password) ?: 1
        }
        if (userId != -1) {
            _uiState.value = uiState.value.copy(loggedIn = true)
            sharedPreferences.edit().putString("user_id", userId.toString()).apply()
            Timber.e("INSERTED ID  $userId")
        } else {
            _uiState.value.copy(error = LoginScreenState.Error.NetworkError)
        }
    }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {

            is LoginScreenEvent.OnEmailTextFieldValueChange -> {
                _uiState.value = _uiState.value.copy(email = event.email, incorrectEmail = false)
            }
            is LoginScreenEvent.OnPasswordTextFieldValueChange -> {
                _uiState.value = uiState.value.copy(password = event.password, incorrectPassword = false)
            }
            LoginScreenEvent.OnLoginButtonClicked -> {
                    _uiState.value = uiState.value.copy(isLoading = true)
                    checkUserEmail(uiState.value.email)
                    if (!uiState.value.incorrectEmail) {
                        checkUserPassword(uiState.value.email, uiState.value.password)
                        if (!uiState.value.incorrectPassword) {
                            getUserId(uiState.value.email, uiState.value.password)
                            if (!sharedPreferences.getString("user_id", "").isNullOrEmpty()
                                && sharedPreferences.getString("user_id", "")?.toInt() != -1
                            ) {
                                _uiState.value = uiState.value.copy(loggedIn = true)
                            } else if (sharedPreferences.getString("user_id", "")?.toInt() == -1) {
                                viewModelScope.launch {
                                    if (userRepository.getUsers().map { it.userName }.contains(_uiState.value.email)) {
                                        _uiState.value = uiState.value.copy(incorrectPassword = true)
                                    } else {
                                        _uiState.value = uiState.value.copy(incorrectEmail = true)
                                    }
                                }
                            }
                        } else {
                            _uiState.value = uiState.value.copy(
                                incorrectPassword = true,
                                error = LoginScreenState.Error.InputEmpty
                            )
                        }
                    } else {
                        _uiState.value = uiState.value.copy(
                            incorrectEmail = true,
                            error = LoginScreenState.Error.InputEmpty
                        )
                    }
                    _uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }

}