package com.android.quizzy.presentation.registration_form


data class OnboardingState(
    var email: String = "",
    var password: String = "",
    var username: String = "",
    var firstname: String = "",
    val isLoading: Boolean = false,
    val error: Error? = null,
    val incorrectEmail: Boolean = false,
    val incorrectUsername: Boolean = false,
    val incorrectPassword: Boolean = false,
    val incorrectRepeatedPassword: Boolean = false,
    val userNameTaken: Boolean = false,
    val proceed: Boolean = false,
)