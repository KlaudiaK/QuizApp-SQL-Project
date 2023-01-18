package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.presentation.profile.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = mutableStateOf(ProfileScreenState())
    val uiState: State<ProfileScreenState> = _uiState

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            val userId = sharedPreferences.getString("user_id", "")
            if (!userId.isNullOrEmpty()) {
                val user  = userRepository.getUser(userId)
                _uiState.value = _uiState.value.copy(
                    username = user.userName,
                    email = user.email,
                    points = user.totalPoints.toString(),
                    solvedQuizzes = user.solvedQuizes.toString(),
                    createdQuizzes = user.createdQuizes.toString()
                )

            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            sharedPreferences.edit().remove("user_id").apply()
        }
    }
}