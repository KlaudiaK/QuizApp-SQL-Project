package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.domain.model.User
import com.android.quizzy.domain.model.UserPassword
import com.android.quizzy.domain.model.UserSettings
import com.android.quizzy.presentation.profile.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _uiState = mutableStateOf(User(-1, "", "", ""))
    val uiState: State<User> = _uiState
    private val _settings = mutableStateOf(UserSettings(-1))
    val settings: State<UserSettings> = _settings
    private val _password = mutableStateOf(UserPassword(-1))
    val password: State<UserPassword> = _password

    init {
        getUserInfo()
    }
    fun saveUser() {
        viewModelScope.launch {
            val user = User(
                id = uiState.value.id,
                userName = uiState.value.userName,
                email = uiState.value.email,
                name = uiState.value.name,
                createdQuizes = uiState.value.createdQuizes,
                solvedQuizes = uiState.value.solvedQuizes,
                totalPoints = uiState.value.totalPoints,
                avatar = uiState.value.avatar,
                rank = uiState.value.rank)
            val userSettings = UserSettings(settings.value.userReferenceId, settings.value.darkMode, settings.value.preferredLanguage)
            val userPassword = UserPassword(userReferenceId = password.value.userReferenceId, password = password.value.password, username = password.value.username)
            userRepository.editUser(user).enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Timber.v("retrofit", "call failed")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Timber.v("retrofit", "call succeded")
                }

            })
            userRepository.updatePassword(userPassword).enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Timber.v("retrofit", "call failed")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Timber.v("retrofit", "call succeded")
                }

            })
            userRepository.updateSettings(userSettings).enqueue(object : Callback<UserSettings> {
                override fun onFailure(call: Call<UserSettings>?, t: Throwable?) {
                    Timber.v("retrofit", "call failed")
                }

                override fun onResponse(call: Call<UserSettings>, response: Response<UserSettings>) {
                    Timber.v("retrofit", "call succeded")
                }

            })
        }
    }
    private fun getUserInfo() {
        viewModelScope.launch {
            val userId = sharedPreferences.getString("user_id", "")
            if (!userId.isNullOrEmpty()) {
                val user  = userRepository.getUser(userId)
                _uiState.value = uiState.value.copy(
                    id = user.id,
                    userName = user.userName,
                    email = user.email,
                    name = user.name,
                    createdQuizes = user.createdQuizes,
                    solvedQuizes = user.solvedQuizes,
                    totalPoints = user.totalPoints,
                    avatar = user.avatar,
                    rank = user.rank
                )
                val userSettings = userRepository.getSettings().filter { it.userReferenceId == userId.toInt() }[0]
                _settings.value = settings.value.copy(
                    userSettings.userReferenceId,
                    userSettings.darkMode,
                    userSettings.preferredLanguage
                )
                val userPassword = userRepository.getPassword(userId.toInt())
                _password.value = password.value.copy(
                    userReferenceId = userPassword.userReferenceId,
                    password = userPassword.password,
                    username = userPassword.username,
                    lastModified = userPassword.lastModified
                )
            }
        }
    }

    fun changeEmail(email: String) {
        _uiState.value = uiState.value.copy(email = email)
    }

    fun changeName(it: String) {
        _uiState.value = uiState.value.copy(name = it)
    }

    fun changePassword(it: String) {
        _password.value = password.value.copy(password = it)

    }

    fun changeAvatar(it: String) {
        _uiState.value = uiState.value.copy(avatar = it)
    }

    fun changePreferredLanguage(it: String) {
        if (it.length > 2) {
            _settings.value = settings.value.copy(preferredLanguage = it.take(2))
        } else {
            _settings.value = settings.value.copy(preferredLanguage = it)
        }
    }

    fun changeDarkMode(it: String) {
        if (it.isNotEmpty()) {
            _settings.value = settings.value.copy(darkMode = it[0])
        } else {
            _settings.value = settings.value.copy(darkMode = null)

        }
    }
}