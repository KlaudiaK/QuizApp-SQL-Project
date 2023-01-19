package com.android.quizzy.domain.model

data class UserSettings (
    val userReferenceId: Int,
    val darkMode: Char? = 'N',
    val preferredLanguage: String? = "EN"
)