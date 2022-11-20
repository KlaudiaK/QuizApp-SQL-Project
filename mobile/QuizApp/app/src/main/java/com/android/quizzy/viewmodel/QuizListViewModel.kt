package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.quizzy.presentation.quiz_list.QuizListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuizListViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = mutableStateOf(QuizListScreenState())
    val uiState: State<QuizListScreenState> = _uiState

    init {}

}