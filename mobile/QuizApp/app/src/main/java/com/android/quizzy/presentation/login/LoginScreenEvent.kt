package com.android.quizzy.presentation.login

sealed class LoginScreenEvent {
    class OnEmailTextFieldValueChange(val email: String ): LoginScreenEvent()
    class OnPasswordTextFieldValueChange(val password: String ): LoginScreenEvent()
    object OnLoginButtonClicked: LoginScreenEvent()
}