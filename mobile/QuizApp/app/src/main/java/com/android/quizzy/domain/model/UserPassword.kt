package com.android.quizzy.domain.model

import java.time.LocalDate

data class UserPassword (
    val userReferenceId: Int,

    val password: String? = "",

    val username: String? = null,

    val lastModified: String = LocalDate.now().toString()
)