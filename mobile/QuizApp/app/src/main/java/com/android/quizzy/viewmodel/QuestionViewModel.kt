package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.quizzy.domain.model.Answer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = mutableStateOf(QuestionScreenState())
    val uiState: State<QuestionScreenState> = _uiState
    private val _list = mutableStateListOf<Answer>(
        Answer("", false),
        Answer("", false),
        Answer("", false),
        Answer("", false)
    )
    val list: List<Answer> = _list

    init {
        getUsername()
    }

    private fun getUsername() {}
    fun onQuestionChanged(question: String) {
        _uiState.value = _uiState.value.copy(question = question)
    }

    fun onAnswerChangedChanged(answer: String, index: Int) {
        _list[index] = _list[index].copy(content = answer)
    }

    fun onCorrectAnswerChanged(correct: Int) {
        _list[correct] = _list[correct].copy(isCorrect = true)
        for (i in 0..3) {
            if (i != correct) {
                _list[i] = _list[i].copy(isCorrect = false)
            }
        }
    }


}

data class QuestionScreenState(
    val question: String = "",
)