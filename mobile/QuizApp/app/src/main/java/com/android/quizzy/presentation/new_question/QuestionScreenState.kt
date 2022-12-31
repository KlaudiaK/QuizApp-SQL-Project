package com.android.quizzy.presentation.new_question

import com.android.quizzy.domain.model.Answer


data class QuestionScreenState(
    val question: String = "",
    val answers: List<Answer>? = listOf(),
    val image: String = ""
)