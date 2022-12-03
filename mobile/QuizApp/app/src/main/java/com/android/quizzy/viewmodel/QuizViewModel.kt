package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.quizzy.presentation.add_new_quiz.NewQuizInputErrors
import com.android.quizzy.presentation.add_new_quiz.QuizScreenState
import com.android.quizzy.presentation.registration_form.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    // private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizScreenState())
    val uiState: State<QuizScreenState> = _uiState

    private val _inputErrors = mutableStateOf(NewQuizInputErrors())
    val inputErrors: State<NewQuizInputErrors> = _inputErrors


    init {
        getUsername()
    }

    private fun getUsername() {}
    fun onTitleChanged(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onCategoryChanged(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun onPrivacySettingsChanged(privacySettings: String) {
        _uiState.value = _uiState.value.copy(privacySettings = privacySettings)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onPointsChanged(points: Int) {
        _uiState.value = _uiState.value.copy(points = points)
    }
    fun onDifficultyLevelChanged(difficulty: String) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }


    fun onContinueClick() {
        validateAllFields()
        //_events.send(ScreenEvent.ShowToast(R.string.success))
    }

    private fun validateAllFields() : Boolean {
        val titleErrorId = InputValidator.getTitleErrorIdOrNull(_uiState.value.title)
        val pointsErrorId = InputValidator.getPointsErrorIdOrNull(_uiState.value.points.toString())
        val categoryErrorId = InputValidator.getCategoryErrorIdOrNull(_uiState.value.category)
        val difficultyErrorId = InputValidator.getDifficultyLevelIdOrNull(_uiState.value.difficulty)
        return if (titleErrorId == null && pointsErrorId == null  && categoryErrorId == null && difficultyErrorId == null) {
            true
        } else {
            _inputErrors.value = _inputErrors.value.copy(titleErrorId = titleErrorId,
                pointsErrorId = pointsErrorId, categoryErrorId = categoryErrorId, difficultyErrorId = difficultyErrorId)
            false
        }
    }

}