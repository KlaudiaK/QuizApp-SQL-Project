package com.android.quizzy.domain.model

import java.time.LocalDate
import kotlin.random.Random

data class Question(
    val questionId: Long = Random(20).nextLong().mod(20).toLong(),
    val content: String,
    val image: String?,
    val creationDate: LocalDate,
    val modificationDate: LocalDate,
    val quizReferenceId: Long
)
