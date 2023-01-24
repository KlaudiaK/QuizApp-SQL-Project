package com.android.quizzy.viewmodel

import androidx.lifecycle.ViewModel
import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.domain.reponse.RankResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class RanksViewModel @Inject constructor(
    val quizRepository: QuizRepository
): ViewModel() {

    fun getRanks() = flow {
        emit(quizRepository.getRanks())
    }.distinctUntilChanged()
}