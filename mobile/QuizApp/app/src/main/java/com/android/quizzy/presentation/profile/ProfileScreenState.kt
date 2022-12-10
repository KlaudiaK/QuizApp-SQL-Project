package com.android.quizzy.presentation.profile

data class ProfileScreenState(
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val points: String = "0",
    val createdQuizzes: String = "0",
    val solvedQuizzes: String = "0",
)