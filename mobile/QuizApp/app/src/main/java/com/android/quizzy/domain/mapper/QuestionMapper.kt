package com.android.quizzy.domain

import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.reponse.QuestionResponse
import java.time.LocalDate

fun Question.mapToQuestionResponse() =
    with(this) {
        QuestionResponse(
            id = questionId,
            content = content,
            creationDate = creationDate.toString(),
            modificationDate = modificationDate.toString(),
            quizReferenceId = quizReferenceId,
            image = image
        )

    }



fun QuestionResponse.mapToQuestion() =
    with(this) {
        Question(
            questionId = id,
            content = content,
            creationDate = LocalDate.parse(creationDate),
            modificationDate = LocalDate.parse(modificationDate),
            quizReferenceId = quizReferenceId,
            image = image
        )

    }
