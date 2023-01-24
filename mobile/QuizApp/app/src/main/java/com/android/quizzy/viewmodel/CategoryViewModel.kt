package com.android.quizzy.viewmodel

import androidx.lifecycle.ViewModel
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.reponse.CategoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    val quizRepository: QuizRepository
): ViewModel() {

    fun getCategories() = flow {
        emit(quizRepository.getCategories())
    }.distinctUntilChanged()
}