package pl.poznan.put.quizzy.quizzes.model.api

import java.time.LocalDate

data class QuizzResponse(
    val id : Long,
    val name: String,
    val description: String?,
    val image: String?,
    val points: Int,
    val likes: Int = 0,
    val creationDate: String? = LocalDate.now().toString(),
    val modificationDate: String? = LocalDate.now().toString(),
    val privacySettings: String?,
    val categoryName: String,
    val difficultyLevelReferenceId: Int,
    val creatorId: Int
)
