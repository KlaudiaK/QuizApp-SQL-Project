package com.android.quizzy.viewmodel

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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(ProfileScreenState())
    val uiState: State<ProfileScreenState> = _uiState

    init {
        getUsername()
    }

    private fun getUsername() {
        viewModelScope.launch {
            val user  = userRepository.getUser("1")
            _uiState.value = _uiState.value.copy(
                username = user.username,
                email = user.email
            )
        }
    }
}