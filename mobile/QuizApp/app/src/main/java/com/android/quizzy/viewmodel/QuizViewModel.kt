package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    // private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {

    private val _uiState = mutableStateOf(QuizScreenState())
    val uiState: State<QuizScreenState> = _uiState

    init{
        getUsername()
    }
    private fun getUsername() {}
    fun onTitleChanged(title: String){_uiState.value = _uiState.value.copy(title = title)}
    fun onCategoryChanged(category: String) {_uiState.value = _uiState.value.copy(category = category)}
    fun onSharingOptionChanged(sharingOption:String){_uiState.value = _uiState.value.copy(sharingOption = sharingOption)}
    fun onDescriptionChanged(description: String) {_uiState.value = _uiState.value.copy(description = description)}
}

data class QuizScreenState(
    val title: String = "",
    val username: String = "",
    val category: String ="",
    val difficulty: String = "",
    val points: Int = 0,
    val sharingOption: String= "",
    val description: String = "",
)