package com.android.quizzy.presentation.details

import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.model.Quiz

data class QuizDetailsScreenState(
    val username: String? = "",
    val quiz: Quiz? = null,
    val questions: List<Question> = listOf()
)
