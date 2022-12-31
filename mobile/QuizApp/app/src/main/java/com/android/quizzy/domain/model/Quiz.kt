package com.android.quizzy.domain.model

import java.time.LocalDate

data class Quiz(
    val id: Long,
    val title: String,
    val image: String?,
    val author: String,
    val description: String? = null,
    val category: String? = null,
    val difficulty: String? = null,
    val points: Int? = 0,
    val likes: Int? = 0,
    val creationDate: LocalDate? = null,
    val modificationDate: LocalDate? = null,
    val sharing: PrivacySetting? = null
)