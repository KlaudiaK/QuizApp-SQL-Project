package com.android.quizzy.domain.model

import java.time.LocalDate
import kotlin.random.Random

data class Question(
    val questionId: Int = Random(20).nextInt().mod(20),
    val content: String,
    val image: String?,
    val creationDate: LocalDate,
    val modificationDate: LocalDate
) {
    companion object {
        val sampleQuestion = Question(
            content = "Which answer is correct?",
            image = "https://cdn-1.motorsport.com/images/amp/Y99yDpLY/s1000/2022-f1-car-1.jpg",
            creationDate = LocalDate.now().minusDays(4),
            modificationDate = LocalDate.now().minusDays(1)
        )
    }
}
