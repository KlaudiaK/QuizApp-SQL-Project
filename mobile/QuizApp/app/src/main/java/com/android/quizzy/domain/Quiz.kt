package com.android.quizzy.domain

data class Quiz(
    val id: Int,
    val title: String,
    val imageURL: String?,
    val author: String
) {
    companion object {

        val sampleQuiz = Quiz(
            id = 12, title = "Sample title",
            imageURL = "https://us.123rf.com/450wm/igornelson/igornelson1807/igornelson180700087/114801534-quiz-w-komiksowym-stylu-quiz-m%C4%85dry-projekt-wektor-gry-.jpg?ver=6",
            author = "Whoever"
        )
    }
}
