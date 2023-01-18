package com.android.quizzy.domain.model

data class UserRegister(
    val username: String,
    val name: String,
    val password: String,
    val email: String,
    val avatar: String? = null
)

data class RegistryResponse(
    val value: String
)