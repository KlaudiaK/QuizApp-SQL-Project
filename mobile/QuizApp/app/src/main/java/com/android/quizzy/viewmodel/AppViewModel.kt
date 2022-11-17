package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.user_repository.UserRepositoryImpl
import com.android.quizzy.presentation.login.LoginScreenEvent
import com.android.quizzy.presentation.login.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {


    private val _uiState = mutableStateOf(LoginScreenState())
    val uiState: State<LoginScreenState> = _uiState


    private fun checkUserEmail(email: String) {
        viewModelScope.launch {

        }
    }

    private fun checkUserPassword(email: String, password: String) {
        viewModelScope.launch {

        }

    }

    private fun getUsernameAndUserName(email: String) {
        viewModelScope.launch {

        }
    }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {

            is LoginScreenEvent.OnEmailTextFieldValueChange -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }
            is LoginScreenEvent.OnPasswordTextFieldValueChange -> {
                _uiState.value = uiState.value.copy(password = event.password)
            }
            LoginScreenEvent.OnLoginButtonClicked -> {
                checkUserEmail(uiState.value.email)
                if (!uiState.value.incorrectEmail) {

                    checkUserPassword(uiState.value.email, uiState.value.password)
                    if (!uiState.value.incorrectPassword) {
                        //    preferences.saveUserEmail(uiState.value.email)
                        //   preferences.saveShouldShowOnboarding(false)
                        //   preferences.saveShouldShowHome(true)
                        getUsernameAndUserName(uiState.value.email)
                    } else {
                        // _uiState.value = uiState.value.copy(incorrectPassword = true)
                    }
                } else {
                    //  _uiState.value = uiState.value.copy(incorrectEmail = true)
                }
            }
        }
    }

}