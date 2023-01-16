package pl.poznan.put.quizzy.questions.model.api

import java.time.LocalDate

data class QuestionResponse(
    val id: Long,
    val content: String,
    val image: String?,
    val creationDate: String? = LocalDate.now().toString(),
    val modificationDate: String? = LocalDate.now().toString(),
    val quizReferenceId: Int
)
