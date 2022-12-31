package com.android.quizzy.presentation.add_new_quiz

data class NewQuizInputErrors(
    val titleErrorId: Int? = null,
    val pointsErrorId: Int? = null,
    val categoryErrorId: Int? = null,
    val difficultyErrorId: Int? = null,
    val imageErrorId: Int? = null
)