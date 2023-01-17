package com.android.quizzy.domain.model

data class User(
    val id: Int,
    val userName: String,
    val firstname: String,
    val email: String,
    val avatar: String? = null
)