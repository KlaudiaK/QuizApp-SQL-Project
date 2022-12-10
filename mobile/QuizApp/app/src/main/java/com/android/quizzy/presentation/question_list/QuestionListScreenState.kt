package com.android.quizzy.presentation.question_list

import com.android.quizzy.domain.model.Question

data class QuestionListScreenState(
    val questions: List<Question> = listOf()
)
