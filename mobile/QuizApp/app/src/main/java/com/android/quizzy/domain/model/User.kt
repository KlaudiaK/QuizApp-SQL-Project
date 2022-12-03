package com.android.quizzy.domain.model

data class User(
    val username: String,
    val firstname: String,
    val email: String,
    val avatar: String? = null
)