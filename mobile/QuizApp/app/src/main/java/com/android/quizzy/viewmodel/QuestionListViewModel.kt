package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.mapToQuestion
import com.android.quizzy.domain.model.Answer
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

    private val _data = mutableStateListOf<Map<Question, List<Answer>>>()
    val data: List<Map<Question, List<Answer>>> = _data

    private val _answers = mutableStateListOf<Answer>()
    val answers: List<Answer> = _answers
    fun getQuestions(quizId: String) {
        viewModelScope.launch {
            // _uiState.value = _uiState.value.copy(questions = emptyList())
            val questionList = quizRepository.getQuestionsForQuiz(quizId).map { it.mapToQuestion() }
            _uiState.value = _uiState.value.copy(questions = questionList)
            questionList.forEach {
                getAnswersForQuestion(it.questionId)
            }
            _questions.apply {
                addAll(questionList)
            }

        }
    }

    fun getAnswers(id: Long): List<Answer> {
        return answers.filter { it.questionReference == id }
    }

    fun deleteQuestion(questionId: Long, quizId: String) {
        viewModelScope.launch {
            quizRepository.deleteQuestionFromQuiz(questionId.toString())
            getQuestions(quizId)
        }
    }

    fun getAnswersForQuestion(questionId: Long) {
        viewModelScope.launch {
            val answers = quizRepository.getAnswersForQuestion(questionId.toString())
            _answers.apply {
                clear()
                addAll(
                    answers
                )
            }
        }
    }

    fun getAnswers(quizId: String) {
        viewModelScope.launch {
            val questions = quizRepository.getQuestionsForQuiz(quizId).map { it.mapToQuestion() }
            questions.forEach {
                val answers = quizRepository.getAnswersForQuestion(it.questionId.toString())
                _data.toMutableList().add(
                    mapOf(Pair(it, answers))
                )
            }

        }

    }
}