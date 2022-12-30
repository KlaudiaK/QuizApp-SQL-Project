package com.android.quizzy.domain.reponse

import java.time.LocalDate

data class QuizResponse(
    val categoryName: String,
    val creationDate: String,
    val creatorId: Int,
    val description: String,
    val difficultyLevelReferenceId: Int,
    val id: Int,
    val image: String,
    val likes: Int,
    val modificationDate: String,
    val name: String,
    val points: Int,
    val privacySettings: String
)
