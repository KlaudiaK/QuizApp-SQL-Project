package com.android.quizzy.domain.reponse

data class QuizResponse(
    val categoryName: String,
    val creationDate: String? = null,
    val creatorId: Int,
    val description: String,
    val difficultyLevelReferenceId: Int,
    val id: Long,
    val image: String,
    val likes: Int? = null,
    val modificationDate: String,
    val name: String,
    val points: Int,
    val privacySettings: String
)
