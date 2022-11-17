package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.quizzy.data.repository.user_repository.UserRepositoryImpl
import com.android.quizzy.presentation.profile.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {

    private val _uiState = mutableStateOf(ProfileScreenState())
    val uiState: State<ProfileScreenState> = _uiState

    init{
        getUsername()
    }
    private fun getUsername() {}
}