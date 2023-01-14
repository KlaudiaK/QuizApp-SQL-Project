package pl.poznan.put.quizzy.solvedQuizzes.mapper

import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizResponse
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizz
import java.time.LocalDate

fun SolvedQuizResponse.mapToSolvedQuiz() = with(this) {
    SolvedQuizz(
        quizReferenceId = quizReferenceId,
        userReferenceId = userReferenceId,
        date = LocalDate.parse(date)
    )
}

fun SolvedQuizz.mapToSolvedQuizResponse() = with(this) {
    SolvedQuizResponse(
        quizReferenceId = quizReferenceId,
        userReferenceId = userReferenceId,
        date = date.toString()
    )
}