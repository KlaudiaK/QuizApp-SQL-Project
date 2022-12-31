package pl.poznan.put.quizzy.questions.model.api

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class QuestionResponse(
    val id: Long,
    val content: String,
    val image: String?,
    val creationDate: String? = LocalDate.now().toString(),
    val modificationDate: String? = LocalDate.now().toString(),
    val quizReferenceId: Long
)
