package com.android.quizzy.domain.model

import java.time.LocalDate

data class Question(
    val content: String,
    val image: String?,
    val creationDate: LocalDate,
    val modificationDate: LocalDate
)
