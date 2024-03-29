package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.mapper.mapToCategory
import com.android.quizzy.domain.model.DifficultyLevel
import com.android.quizzy.domain.model.PrivacySetting
import com.android.quizzy.domain.model.Question
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
    private val quizRepository: QuizRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizScreenState())
    val uiState: State<QuizScreenState> = _uiState

    private val _inputErrors = mutableStateOf(NewQuizInputErrors())
    val inputErrors: State<NewQuizInputErrors> = _inputErrors

    private val _questionList = mutableStateListOf<Question>()

    init {
        getUsername()
        getCategories()
    }

    fun addQuestionToList(question: Question) {
        _questionList.toMutableList().add(question)
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
        val userId = sharedPreferences.getString("user_id", "")
        viewModelScope.launch {
            with(_uiState.value) {
                val newQuiz = QuizResponse(
                    id = Random(100).nextInt(20, 2000).toLong(),
                    name = title,
                    description = description,
                    points = points,
                    difficultyLevelReferenceId = quizRepository.getDifficultyLevels()
                        .find { it.name == difficulty }?.id ?: 1,
                    categoryName = category,
                    creatorId = userId?.toInt() ?: 1,
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
                    difficulty = difficulty ?: DifficultyLevel.MEDIUM.name,
                    image = image ?: ""
                )
            }
        }
    }

    fun getNumberOfQuestions(id: String) {
        viewModelScope.launch {
            val count = quizRepository.getQuestionsForQuiz(id).size
            _uiState.value = _uiState.value.copy(numberOfQuestions = count)
        }
    }

    fun editQuiz(id: Long) {
        viewModelScope.launch {
            if (validateAllFields()) {
                val userId = sharedPreferences.getString("user_id", "")
                if (!userId.isNullOrEmpty()) {
                    with(_uiState.value) {
                        quizRepository.updateQuiz(
                            QuizResponse(
                                id = id,
                                name = title,
                                description = description,
                                image = image,
                                modificationDate = LocalDate.now().toString(),
                                points = points,
                                categoryName = category,
                                difficultyLevelReferenceId = quizRepository.getDifficultyLevels()
                                    .find { it.name == difficulty }?.id ?: 1,
                                privacySettings = privacySettings,
                                creatorId = userId.toInt(),
                            )
                        )
                    }
            }

            }
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            val categories = quizRepository.getCategories().map { it.mapToCategory() }
            _uiState.value = _uiState.value.copy(categoriesList = categories)
        }
    }
}