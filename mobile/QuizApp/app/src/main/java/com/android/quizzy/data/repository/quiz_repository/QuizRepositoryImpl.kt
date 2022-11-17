package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.Quiz
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(): QuizRepository {
    override fun getQuizzes(): List<Quiz> {
        TODO("Not yet implemented")
    }

}