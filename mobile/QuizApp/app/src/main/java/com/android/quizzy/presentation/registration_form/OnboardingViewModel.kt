package com.android.quizzy.presentation.registration_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.quizzy.data.repository.user_repository.UserRepositoryImpl
import com.android.quizzy.presentation.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {
    private val _uiState = mutableStateOf(OnboardingState())
    val uiState: State<OnboardingState> = _uiState

    fun getStartRoute(): Route {
       return LoginScreenDestination
    }
}
