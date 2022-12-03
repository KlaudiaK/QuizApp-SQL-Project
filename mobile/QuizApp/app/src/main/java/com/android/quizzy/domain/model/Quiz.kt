package com.android.quizzy.domain.model

import java.time.LocalDate

data class Quiz(
    val id: Int,
    val title: String,
    val image: String?,
    val author: String,
    val description: String? = null,
    val category: String? = null,
    val difficulty: String? = null,
    val points: Int? = null,
    val likes: Int? = null,
    val creationDate: LocalDate? = null,
    val modificationDate: LocalDate? = null,
    val sharing: PrivacySetting? = null
) {
    companion object {
        const val adres1 =
            "https://static.designboom.com/wp-content/uploads/2022/05/strozzi-nft-db-500.jpg"
        val url2 =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHlPV78oDliBqT3lBJ3OyP5ENC9ixCZYa6cA&usqp=CAU"
        val sampleQuiz = Quiz(
            id = 12, title = "Sample title",
            image = "https://us.123rf.com/450wm/igornelson/igornelson1807/igornelson180700087/114801534-quiz-w-komiksowym-stylu-quiz-m%C4%85dry-projekt-wektor-gry-.jpg?ver=6",
            author = "Whoever",
            category = Categories.ART.name
        )

        val sampleQuiz1 = Quiz(
            id = 1, title = "Sample title",
            image = adres1,
            author = "Whoever",
            category = Categories.ART.name
        )
        val sampleQuiz2 = Quiz(
            id = 2, title = "Sample title but another",
            image = "https://us.123rf.com/450wm/igornelson/igornelson1807/igornelson180700087/114801534-quiz-w-komiksowym-stylu-quiz-m%C4%85dry-projekt-wektor-gry-.jpg?ver=6",
            author = "Whoever but another",
            category = Categories.ANIMALS.name
        )
        val sampleQuiz3 = Quiz(
            id = 3, title = "Sample title but another than prev",
            image = url2,
            author = "Whoever but another than prev",
            category = Categories.TRAVEL.name
        )
        val sampleQuizList = listOf(
            sampleQuiz1, sampleQuiz2, sampleQuiz3
        )
    }
}