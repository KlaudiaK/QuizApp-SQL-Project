package pl.poznan.put.quizzy.questions.model.mapper

import pl.poznan.put.quizzy.questions.model.api.QuestionResponse
import pl.poznan.put.quizzy.questions.model.db.Question
import java.time.LocalDate

fun Question.mapToApiModel() = with(this) {
    QuestionResponse(
        id = id,
        content = content,
        creationDate = creationDate.toString(),
        modificationDate = modificationDate.toString(),
        image = image,
        quizReferenceId = quizReferenceId
    )
}

fun QuestionResponse.mapToDBModel() = with(this) {
    Question(
        id = id,
        content = content,
        creationDate = LocalDate.parse(creationDate),
        modificationDate = LocalDate.parse(modificationDate),
        image = image,
        quizReferenceId = quizReferenceId
    )
}