package com.android.quizzy.domain.model

data class Answer(
    var id: Long,
    var content: String,
    var isCorrect: Boolean,
    var questionReference: Long
)
