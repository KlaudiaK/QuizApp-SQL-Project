package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.Quiz

interface QuizRepository {
    fun getQuizzes(): List<Quiz>
}