package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.model.PrivacySetting
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.presentation.quiz_list.QuizListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizListScreenState())
    val uiState: State<QuizListScreenState> = _uiState

    init {
        getQuizzes()
    }

     fun getQuizzes(){
        viewModelScope.launch {
            val list = quizRepository.getQuizzes()
            _uiState.value = _uiState.value.copy(quizItems = list)
        }
    }

    fun getFilteredQuizes(privacySetting: PrivacySetting) : List<Quiz> {
        return _uiState.value.quizItems?.filter { it.sharing?.name == privacySetting.name } ?: emptyList()
    }
}