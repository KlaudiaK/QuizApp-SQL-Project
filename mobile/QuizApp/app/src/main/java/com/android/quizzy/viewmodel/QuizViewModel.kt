package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.domain.model.DifficultyLevel
import com.android.quizzy.domain.model.PrivacySetting
import com.android.quizzy.domain.reponse.QuizResponse
import com.android.quizzy.presentation.add_new_quiz.NewQuizInputErrors
import com.android.quizzy.presentation.add_new_quiz.QuizScreenState
import com.android.quizzy.presentation.registration_form.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val quizRepository: QuizRepository
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

    fun onImageChanged(image: String) {
        _uiState.value = _uiState.value.copy(image = image)
    }

    fun onContinueClick(navigateBack: () -> Unit) {
        viewModelScope.launch {
            with(_uiState.value) {
                val newQuiz = QuizResponse(
                    id = Random(100).nextInt(20, 2000),
                    name = title,
                    description = description,
                    points = points,
                    difficultyLevelReferenceId = quizRepository.getDifficultyLevels()
                        .find { it.name == difficulty }?.id ?: 1,
                    categoryName = category,
                    creatorId = 1289,
                    image = image,
                    likes = 0,
                    privacySettings = privacySettings,
                    modificationDate = LocalDate.now().toString(),
                    creationDate = LocalDate.now().toString()
                )
                if (validateAllFields()) {
                    quizRepository.addNewQuiz(newQuiz)
                    navigateBack()
                }
            }

        }
        //_events.send(ScreenEvent.ShowToast(R.string.success))
    }

    private fun validateAllFields(): Boolean {
        val titleErrorId = InputValidator.getTitleErrorIdOrNull(_uiState.value.title)
        val pointsErrorId = InputValidator.getPointsErrorIdOrNull(_uiState.value.points.toString())
        val categoryErrorId = InputValidator.getCategoryErrorIdOrNull(_uiState.value.category)
        val difficultyErrorId = InputValidator.getDifficultyLevelIdOrNull(_uiState.value.difficulty)
        val imageErrorId = InputValidator.getImageIdOrNull(_uiState.value.image)
        return if (titleErrorId == null && pointsErrorId == null && categoryErrorId == null && difficultyErrorId == null && imageErrorId == null) {
            true
        } else {
            _inputErrors.value = _inputErrors.value.copy(
                titleErrorId = titleErrorId,
                pointsErrorId = pointsErrorId,
                categoryErrorId = categoryErrorId,
                difficultyErrorId = difficultyErrorId,
                imageErrorId = imageErrorId
            )
            false
        }
    }

    fun getQuizInEditMode(id: String) {
        viewModelScope.launch {
            val quiz = quizRepository.getQuizById(id)
            with(quiz) {
                _uiState.value = _uiState.value.copy(
                    title = title,
                    description = description ?: "",
                    category = category ?: "",
                    privacySettings = sharing?.name ?: PrivacySetting.PUBLIC.name,
                    points = points ?: 0,
                    difficulty = difficulty ?: DifficultyLevel.MEDIUM.name
                )
            }

        }
    }

    fun getNumberOfQuestions(id: String){
        viewModelScope.launch {
            val count = quizRepository.getQuestionsForQuiz(id).size
            _uiState.value = _uiState.value.copy(numberOfQuestions = count)
        }
    }

}