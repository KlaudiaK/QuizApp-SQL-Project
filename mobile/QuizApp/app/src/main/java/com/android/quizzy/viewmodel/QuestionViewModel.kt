package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.reponse.QuestionResponse
import com.android.quizzy.presentation.new_question.NewQuestionInputErrors
import com.android.quizzy.presentation.new_question.QuestionScreenState
import com.android.quizzy.presentation.registration_form.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(QuestionScreenState())
    val uiState: State<QuestionScreenState> = _uiState

    private val _inputErrors = mutableStateOf(NewQuestionInputErrors())
    val inputErrors: State<NewQuestionInputErrors> = _inputErrors

    private val _list = mutableStateListOf(
        Answer(1, "", false),
        Answer(2, "", false), Answer(3, "", false),
        Answer(4, "", false),
    )
    val list: List<Answer> = _list

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

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

    private fun validateAllFields(): Boolean {
        val questionErrorId = InputValidator.getQuestionErrorIdOrNull(_uiState.value.question)
        val answersErrorId = mutableListOf<Int?>(null, null, null, null)
        val imageErrorId =
            if (_uiState.value.image.isNotEmpty()) InputValidator.getImageIdOrNull(_uiState.value.image) else null
        _list.toList().forEachIndexed { index, answer ->
            answersErrorId[index] = InputValidator.getAnswerErrorIdOrNull(answer.content)
        }
        return if (questionErrorId == null && answersErrorId.all { it == null } && imageErrorId == null) {
            true
        } else {
            _inputErrors.value = _inputErrors.value.copy(
                questionErrorId = questionErrorId,
                answersErrorId = answersErrorId, imageErrorId = imageErrorId
            )
            false
        }
    }

    fun onAddNewQuestionClicked() {
        validateAllFields()
    }

    fun onSaveClicked(quizId: Long) {
        viewModelScope.launch {
            if (validateAllFields()) {
                val maxId = quizRepository.getMaxQuestionId() + 1L
                val question = QuestionResponse(
                    id = maxId,
                    content = _uiState.value.question,
                    image = _uiState.value.image,
                    modificationDate = LocalDate.now().toString(),
                    creationDate = LocalDate.now().toString(),
                    quizReferenceId = quizId
                )
                quizRepository.addQuestionForQuiz(question)
                for (answer in list) {
                    val answerWithQuestionId = answer.apply {
                        this.questionReference = question.id
                    }
                    quizRepository.addAnswerForQuestion(answerWithQuestionId)
                }
                sendMessage("You've added new question!")
            }
        }
    }

    fun onImageChanged(image: String) {
        _uiState.value = _uiState.value.copy(image = image)
    }

    fun getQuestion(id: Long) {
        viewModelScope.launch {
            val question = quizRepository.getQuestion(id.toString())
            _uiState.value =
                _uiState.value.copy(question = question.content, image = question.image ?: "")
            val answers = quizRepository.getAnswersForQuestion(question.questionId.toString())
            _list.apply {
                clear()
                addAll(answers)
            }
        }
    }

    fun onEditClicked(id: Long) {
        viewModelScope.launch {
            if (validateAllFields()) {
                val question = quizRepository.getQuestion(id.toString())
                val newQuestion = QuestionResponse(
                    id = question.questionId,
                    content = _uiState.value.question,
                    image = _uiState.value.image,
                    quizReferenceId = question.quizReferenceId,
                    modificationDate = LocalDate.now().toString()
                )

                quizRepository.updateQuestionForQuiz(newQuestion)
                _list.forEach {
                    quizRepository.editAnswerForQuestion(
                        Answer(
                            id = it.id,
                            content = it.content,
                            isCorrect = it.isCorrect,
                            questionReference = id
                        )
                    )
                }
                sendMessage("You've edited the question")
            }
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}

