package com.android.quizzy.presentation.new_question

import com.android.quizzy.presentation.registration_form.InputValidator

data class NewQuestionInputErrors (
    val questionErrorId: Int? = null,
    val answersErrorId: List<Int?> = listOf(null, null, null, null),
    val imageErrorId: Int? = null
)