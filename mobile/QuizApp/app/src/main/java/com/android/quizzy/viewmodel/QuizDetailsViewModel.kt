package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.domain.mapToQuestion
import com.android.quizzy.domain.mapToQuestionResponse
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.DifficultyLevel
import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.reponse.QuestionWithAnswers
import com.android.quizzy.domain.reponse.SolvedQuizResponse
import com.android.quizzy.presentation.details.QuizDetailsScreenState
import com.android.quizzy.ui.theme.easyGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizDetailsScreenState())
    val uiState: State<QuizDetailsScreenState> = _uiState

    val answers =
        MutableLiveData<List<Answer>>()

    val userAnswers =
        MutableLiveData<List<QuestionWithAnswers.QuestionsList>>()

    private val _questionWithAnswers = MutableLiveData<List<QuestionWithAnswers.QuestionsList>>(
        listOf()
    )
    val questionWithAnswers: LiveData<List<QuestionWithAnswers.QuestionsList>> =
        _questionWithAnswers

    private val _questionListState =
        MutableStateFlow<QuestionWithAnswersState>(QuestionWithAnswersState.Initial)
    val questionListState = _questionListState.asStateFlow()

    private val _finalScore = MutableLiveData<Int>(0)
    val finalScore: LiveData<Int> = _finalScore

    fun getQuizDetails(id: String) {
        viewModelScope.launch {
            val quiz = quizRepository.getQuizById(id)
            _uiState.value = _uiState.value.copy(quiz = quiz)
        }
    }

    fun getAuthorization(): Boolean {
        val userId = sharedPreferences.getString("user_id", "")
        if (!userId.isNullOrEmpty()) {
            return userId == uiState.value.quiz?.author
        }
        return false
    }

    fun getDifficultyColor(difficultyLevel: String?): Color =
        DifficultyLevel.values().find { it.name == difficultyLevel }?.color ?: easyGreen

    fun getUsername(id: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(id)
            _uiState.value = _uiState.value.copy(username = user.userName)
        }
    }

    fun getQuestions(quizId: String) {
        viewModelScope.launch {
            answers.value = listOf()
            val questions = quizRepository.getQuestionsForQuiz(quizId).map { it.mapToQuestion() }
            _uiState.value = _uiState.value.copy(questions = questions)
        }
    }

    fun getAnswers(questionId: String) {
        viewModelScope.launch {
            answers.value = listOf()
            answers.value = (quizRepository.getAnswersForQuestion(questionId))
        }
    }

    fun deleteQuiz(quizId: String) {
        viewModelScope.launch {
            quizRepository.deleteQuiz(quizId)
        }
    }

    fun getQuestionWithAnswers(quizId: String) {
        viewModelScope.launch {
            val list = quizRepository.getQuestionWithAnswers(quizId).list
            _questionWithAnswers.value = list
            if (list.isNotEmpty()) _questionListState.value = QuestionWithAnswersState.Success
            else _questionListState.value = QuestionWithAnswersState.NoResult
        }
    }

    fun submitAnswer(question: Question, answer: Answer) {
        val newList = mutableListOf<QuestionWithAnswers.QuestionsList>()
        userAnswers.value?.forEach {
            newList.add(it)
        }
        newList.add(
            QuestionWithAnswers.QuestionsList(
                question.mapToQuestionResponse(),
                listOf(answer)
            )
        )
        userAnswers.value = newList

    }

    fun getFinalScore() {

        var final = 0
        val correctAnswers = _questionWithAnswers.value

        userAnswers.value?.forEach { userAnswer ->
            val answersForQuestion =
                correctAnswers?.find { it.question.id == userAnswer.question.id }?.answers
            if (answersForQuestion?.find { it.id == userAnswer.answers[0].id }?.isCorrect == true) {
                final += 1
            }
        }

        _finalScore.value = final

    }

    fun clearUserAnswers() {
        userAnswers.value = listOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addQuizToSolved(quizId: Long) {
        viewModelScope.launch {
            val userId = sharedPreferences.getString("user_id", "")
            if (!userId.isNullOrEmpty()) {
                quizRepository.addQuizToSolved(
                    SolvedQuizResponse(
                        quizReferenceId = quizId,
                        userReferenceId = userId.toLong(),
                        date = LocalDate.now().toString()
                    )
                )
            }
        }
    }
}

operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}

sealed class QuestionWithAnswersState {
    object NoResult : QuestionWithAnswersState()
    object Initial : QuestionWithAnswersState()
    object Success : QuestionWithAnswersState()
}