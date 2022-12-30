package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.mapToQuestion
import com.android.quizzy.domain.model.Question
import com.android.quizzy.presentation.question_list.QuestionListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionListViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(QuestionListScreenState())
    val uiState: State<QuestionListScreenState> = _uiState

    private val _questions = mutableStateListOf<Question>()
    val questions: List<Question> = _questions

    fun getQuestions(quizId: String) {
        viewModelScope.launch {
            val quizList = quizRepository.getQuestionsForQuiz(quizId).map { it.mapToQuestion() }
            _uiState.value = _uiState.value.copy(questions = quizList)
            _questions.apply {
                addAll(quizList)
            }

        }
    }

    fun deleteQuestion(questionId: Long) {
        var list = _uiState.value.questions
        list = list.toMutableList().apply {
            remove(list.find { it.questionId == questionId })
        }.toList()
        _questions.apply {
            remove(list.find { it.questionId == questionId })
        }

        _uiState.value = _uiState.value.copy(questions = list)

    }
}