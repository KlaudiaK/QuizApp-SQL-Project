package com.android.quizzy.domain.reponse

import java.time.LocalDate

data class SolvedQuizResponse(
    val userReferenceId: Long,
    val date: String = LocalDate.now().toString(),
    val quizReferenceId: Long
)
