package pl.poznan.put.quizzy.solvedQuizzes.model

import java.time.LocalDate

data class SolvedQuizResponse(
    val userReferenceId: Int,
    val date: String = LocalDate.now().toString(),
    val quizReferenceId: Int
)

