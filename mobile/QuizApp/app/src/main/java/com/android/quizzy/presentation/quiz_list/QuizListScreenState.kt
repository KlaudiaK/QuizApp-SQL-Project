package com.android.quizzy.presentation.quiz_list

import com.android.quizzy.domain.model.Quiz

data class QuizListScreenState (
    var query: String = "",
    val category: String = "",
    val selectedCategory:String = "",
    val quizItems: List<Quiz>? = Quiz.sampleQuizList,
    val isRefreshing: Boolean = false,
    val categoryNamesList: List<String> = listOf(),
    val favouriteChecked: Boolean = false,
    val favouriteList: MutableList<String> = mutableListOf<String>(),
    val showFilter: Boolean = false
)