package com.android.quizzy.presentation.login

data class LoginScreenState(
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val error: Error? = null,
    val incorrectEmail:Boolean = false,
    val incorrectPassword:Boolean = false,
    val items : List<String> = listOf()
){
    sealed class Error {
        object NetworkError: Error()
        object InputTooShort: Error()
        object InputEmpty: Error()
    }
}