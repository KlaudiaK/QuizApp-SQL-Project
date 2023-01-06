package com.android.quizzy.domain.reponse

import com.android.quizzy.domain.model.Answer
import com.google.gson.annotations.SerializedName

data class QuestionWithAnswers(
    @SerializedName("question_answers_list")
    val list: List<QuestionsList>
) {
    data class QuestionsList(
        val question: QuestionResponse,
        val answers: List<Answer>
    )
}