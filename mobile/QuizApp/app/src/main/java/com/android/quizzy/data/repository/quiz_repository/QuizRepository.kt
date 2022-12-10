package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getQuizzes(): List<Quiz>

    suspend fun getQuizById(id: String): Quiz

    suspend fun addNewQuiz(quiz: Quiz)

    suspend fun updateQuiz(quiz: Quiz)

    suspend fun deleteQuiz(quiz: Quiz)

    suspend fun addQuizToFavourites(quiz: Quiz)

    suspend fun getFavouriteQuizzes(user: User): List<Quiz>

    suspend fun addQuestionForQuiz(question: Question)

    suspend fun updateQuestionForQuiz(question: Question)

    suspend fun getQuestionsForQuiz(quizId: String): List<Question>

    suspend fun addAnswerForQuestion(answer: Answer)

    suspend fun editAnswerForQuestion(answer: Answer)

    suspend fun getAnswersForQuestion(questionId: String): List<Answer>

    suspend fun deleteQuestionFromQuiz(id: String)
}