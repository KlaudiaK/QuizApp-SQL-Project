package com.android.quizzy.domain.model

data class User(
    val id: Int,
    val userName: String,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val totalPoints: Int = 0,
    val solvedQuizes: Int = 0,
    val createdQuizes: Int = 0,
    val rank: Rank = Rank.BRONZ
    )