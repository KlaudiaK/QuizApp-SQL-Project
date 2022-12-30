package com.android.quizzy.domain.reponse

import java.time.LocalDate

data class QuestionResponse(
    val id: Long,
    val content: String,
    val image: String?,
    val creationDate: String? = LocalDate.now().toString(),
    val modificationDate: String? = LocalDate.now().toString(),
    val quizReferenceId: Long
)
