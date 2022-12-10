package com.android.quizzy.presentation.add_new_quiz

import com.android.quizzy.domain.model.PrivacySetting

data class QuizScreenState(
    val title: String = "",
    val username: String = "",
    val category: String = "",
    val difficulty: String = "",
    val points: Int = 0,
    val privacySettings: String = PrivacySetting.PRIVATE.name,
    val description: String = "",
)