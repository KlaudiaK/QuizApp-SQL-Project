package com.android.quizzy.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UiViewModel @Inject constructor(): ViewModel(){
    private val _uiState = mutableStateOf(false)
    val uiState: State<Boolean> = _uiState

    private val bottomBarVisible = MutableLiveData(false)
    val visibleHolder: LiveData<Boolean> = bottomBarVisible

    fun onBottomBarVisibilityChange(newVisible: Boolean) {
        bottomBarVisible.postValue(newVisible)
    }
}