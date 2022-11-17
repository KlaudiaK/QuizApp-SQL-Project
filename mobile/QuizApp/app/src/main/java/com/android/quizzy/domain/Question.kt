package com.android.quizzy.domain

import java.time.LocalDate

data class Question(
    val content: String,
    val image: String?,
    val creationDate: LocalDate,
    val modificationDate: LocalDate
)
