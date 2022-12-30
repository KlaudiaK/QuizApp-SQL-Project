package com.android.quizzy.domain.reponse

data class DifficultyLevelResponse(
    val description: String,
    val id: Int,
    val name: String,
    val stars: Int
)