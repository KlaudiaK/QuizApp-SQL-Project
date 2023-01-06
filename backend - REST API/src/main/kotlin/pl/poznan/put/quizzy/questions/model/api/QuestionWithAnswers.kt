package pl.poznan.put.quizzy.questions.model.api

import com.fasterxml.jackson.annotation.JsonProperty
import pl.poznan.put.quizzy.answers.model.Answer

data class QuestionWithAnswers(
    @JsonProperty("question_answers_list")
    val list: List<QuestionsList>
) {
    data class QuestionsList(
        val question: QuestionResponse,
        val answers: List<Answer>
    )
}

