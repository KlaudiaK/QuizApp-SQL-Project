package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.DifficultyLevel
import com.android.quizzy.presentation.details.QuizDetailsScreenState
import com.android.quizzy.ui.theme.easyGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizDetailsScreenState())
    val uiState: State<QuizDetailsScreenState> = _uiState

    val answers = mutableListOf<Answer>()

    fun getQuizDetails(id: String) {
        viewModelScope.launch {
            val quiz = quizRepository.getQuizById(id)
            _uiState.value = _uiState.value.copy(quiz = quiz)
        }
    }

    fun getDifficultyColor(difficultyLevel: String?): Color =
        DifficultyLevel.values().find { it.name == difficultyLevel }?.color ?: easyGreen

    fun getUsername(id: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(id)
            _uiState.value = _uiState.value.copy(username = user.username)
        }
    }

    fun getQuestions(quizId: String) {
        viewModelScope.launch{
            val questions  = quizRepository.getQuestionsForQuiz(quizId)
            _uiState.value = _uiState.value.copy(questions = questions)
        }
    }

    fun getAnswers(questionId: Int) {
        viewModelScope.launch {
            answers.addAll(quizRepository.getAnswersForQuestion(questionId.toString()))
        }
    }
}