package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getQuizzes(): Flow<List<Quiz>>

    fun addNewQuiz(quiz: Quiz)

    fun deleteQuiz(quiz: Quiz)

    fun addQuizToFavourites(quiz: Quiz)

    fun getFavouriteQuizzes(user: User): Flow<List<Quiz>>

}