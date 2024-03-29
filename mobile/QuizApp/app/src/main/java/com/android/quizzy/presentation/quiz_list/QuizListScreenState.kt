package com.android.quizzy.presentation.quiz_list

import com.android.quizzy.domain.model.Quiz

data class QuizListScreenState (
    var query: String = "",
    val category: String = "",
    val selectedCategory:String = "",
    val quizItems: List<Quiz>? = emptyList(),
    val isRefreshing: Boolean = false,
    val categoryNamesList: List<String> = listOf(),
    val favouriteChecked: Boolean = false,
    val favouriteList: MutableList<String> = mutableListOf(),
    val showFilter: Boolean = false,
    var userName: String = "",
    var userId: Int = -1
)