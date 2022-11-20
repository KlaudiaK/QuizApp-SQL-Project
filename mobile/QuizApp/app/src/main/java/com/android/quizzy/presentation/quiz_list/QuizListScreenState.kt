package com.android.quizzy.presentation.quiz_list

import com.android.quizzy.domain.Quiz

data class QuizListScreenState (
    var query: String = "",
    val category: String = "",
    val selectedCategory:String = "",
    val quizItems: List<Quiz>? = listOf(Quiz.sampleQuiz, Quiz.sampleQuiz),
    val isRefreshing: Boolean = false,
    val categoryNamesList: List<String> = listOf(),
    val favouriteChecked: Boolean = false,
    val favouriteList: MutableList<String> = mutableListOf<String>(),
    val showFilter: Boolean = false
)