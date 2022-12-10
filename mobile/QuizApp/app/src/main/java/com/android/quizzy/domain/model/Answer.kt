package com.android.quizzy.domain.model

data class Answer(
    var content: String,
    var isCorrect: Boolean
) {
    companion object {
        val listOfAnswers = listOf<Answer>(
            Answer("this is answer 1", false),
            Answer("this is answer 2", false),
            Answer("this is answer 3", true),
            Answer("this is answer 4", false)
        )
    }
}
