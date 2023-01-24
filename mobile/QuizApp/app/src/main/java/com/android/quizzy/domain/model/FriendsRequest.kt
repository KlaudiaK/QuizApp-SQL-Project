package com.android.quizzy.domain.model

import java.time.LocalDate

data class FriendRequest(
    val sent: List<Int> = listOf(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth),
    val fromUserReferenceId: Int,
    val toUserReferenceId: Int,
    val status: String = "Sent"
)
