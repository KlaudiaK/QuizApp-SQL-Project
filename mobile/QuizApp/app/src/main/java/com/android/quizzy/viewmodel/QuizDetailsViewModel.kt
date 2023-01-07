package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
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
import com.android.quizzy.presentation.details.QuizDetailsScreenState
import com.android.quizzy.ui.theme.easyGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizDetailsScreenState())
    val uiState: State<QuizDetailsScreenState> = _uiState

    val answers =
        MutableLiveData<List<Answer>>()

    val userAnswers =
        MutableLiveData<List<QuestionWithAnswers.QuestionsList>>()

    val finalScore = MutableLiveData<Int>(0)
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

    fun loadTaskList(quizId: String): Flow<List<Question>> = flow {
        emit(quizRepository.getQuestionsForQuiz(quizId).map { it.mapToQuestion() })
    }


    fun loadAnswersList(questionId: String): Flow<List<Answer>> = flow {
        emit(quizRepository.getAnswersForQuestion(questionId))
    }

    fun getQuestionWithAnswers(quizId: String): Flow<List<QuestionWithAnswers.QuestionsList>> =
        flow {
            emit(quizRepository.getQuestionWithAnswers(quizId).list)
        }.distinctUntilChanged()

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

    fun getFinalScore() = flow {

        var final = 0
        val correctAnswers =
            quizRepository.getQuestionWithAnswers(_uiState.value.quiz?.id.toString()).list

        userAnswers.value?.forEach { userAnswer ->
            val answersForQuestion =
                correctAnswers.find { it.question.id == userAnswer.question.id }?.answers
            if (answersForQuestion?.find { it.id == userAnswer.answers[0].id }?.isCorrect == true) {
                final += 1
            }
        }

        emit(final)

    }.distinctUntilChanged()

    fun clearUserAnswers() {
        userAnswers.value = listOf()
    }
}

operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}