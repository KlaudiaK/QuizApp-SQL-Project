package com.android.quizzy.presentation.new_question

data class NewQuestionInputErrors (
    val questionErrorId: Int? = null,
    val answersErrorId: List<Int?> = listOf(null, null, null, null),
    val imageErrorId: Int? = null
)