package com.android.quizzy.domain

import java.time.LocalDate

data class Quiz(
    val id: Int,
    val title: String,
    val image: String?,
    val author: String,
    val description: String? = null,
    val category:String? = null,
    val difficulty: String? = null,
    val points: Int? = null,
    val likes: Int? = null,
    val creationDate: LocalDate? = null,
    val modificationDate: LocalDate? = null,
    val sharing: SharingOption? = null
) {
    companion object {

        val sampleQuiz = Quiz(
            id = 12, title = "Sample title",
            image = "https://us.123rf.com/450wm/igornelson/igornelson1807/igornelson180700087/114801534-quiz-w-komiksowym-stylu-quiz-m%C4%85dry-projekt-wektor-gry-.jpg?ver=6",
            author = "Whoever"
        )
    }
}




enum class SharingOption{
    PRIVATE, PUBLIC, FRIENDS
}