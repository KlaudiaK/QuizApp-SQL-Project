package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.domain.model.PrivacySetting
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.presentation.quiz_list.QuizListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val sharedPreferences: SharedPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizListScreenState())
    val uiState: State<QuizListScreenState> = _uiState

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch {
            val userId = sharedPreferences.getString("user_id", "")
            Timber.e("UserId: $userId")
            if (!userId.isNullOrEmpty()) {
                _uiState.value = uiState.value.copy(
                    userId = userId.toInt(),
                    userName = userRepository.getUser(userId).userName
                )
            }
            getQuizzes()
        }
    }

    fun getMyQuizzes() {
        viewModelScope.launch {
            val list = quizRepository.getQuizzes().filter { it.author == _uiState.value.userId.toString() }
            _uiState.value = _uiState.value.copy(quizItems = list)
        }
    }

    fun getQuizzes() {
        viewModelScope.launch {
            val list = quizRepository.getQuizzes()
            _uiState.value = _uiState.value.copy(quizItems = list)
        }
    }


    fun getFilteredQuizes(privacySetting: PrivacySetting, selectedCategory: String? = null): List<Quiz> {
        var result  = _uiState.value.quizItems?.filter { it.sharing?.name == privacySetting.name }
            ?: emptyList()
        selectedCategory?.let {
            if (!it.equals(Categories.OTHER.name, ignoreCase = true)) result = result.filter { it.category.equals(selectedCategory, ignoreCase = true)  }
        }
        return result
    }

    fun getFilterQuizesByCategory(selectedCategory: String): List<Quiz> {
        return _uiState.value.quizItems?.filter { it.category.equals(selectedCategory, ignoreCase = true)  } ?: listOf()
    }

    fun addQuizToFavourites(id: Long){
        viewModelScope.launch {
            quizRepository.addQuizToFavourites(id)
        }
        sendMessage("Quiz added to favourites")
    }

    fun deleteQuizFromFavourites(id: Long) {
        viewModelScope.launch {
            quizRepository.deleteQuizFromFavourites(id)
        }
        sendMessage("Quiz deleted from favourites")
    }

    fun getListOfFavouritesQuizzes(userId: Long) = flow {
        emit(quizRepository.getFavouriteQuizzes(userId))
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}