package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.presentation.new_question.NewQuestionInputErrors
import com.android.quizzy.presentation.new_question.QuestionScreenState
import com.android.quizzy.presentation.registration_form.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = mutableStateOf(QuestionScreenState())
    val uiState: State<QuestionScreenState> = _uiState

    private val _inputErrors = mutableStateOf(NewQuestionInputErrors())
    val inputErrors: State<NewQuestionInputErrors> = _inputErrors

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

    private fun validateAllFields() : Boolean {
        val questionErrorId = InputValidator.getQuestionErrorIdOrNull(_uiState.value.question)
        val answersErrorId = mutableListOf<Int?>(null, null, null, null)
        _list.toList().forEachIndexed { index, answer ->
            answersErrorId[index] = InputValidator.getAnswerErrorIdOrNull(answer.content)
        }
        return if (questionErrorId == null && answersErrorId.all { it == null }) {
            true
        } else {
            _inputErrors.value = _inputErrors.value.copy(questionErrorId = questionErrorId,
                answersErrorId = answersErrorId)
            false
        }
    }

    fun onAddNewQuestionClicked(){
        validateAllFields()
    }

    fun onSaveClicked(){
        validateAllFields()
    }

}