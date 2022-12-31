package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.model.*
import com.android.quizzy.domain.reponse.CategoryResponse
import com.android.quizzy.domain.reponse.DifficultyLevelResponse
import com.android.quizzy.domain.reponse.QuestionResponse
import com.android.quizzy.domain.reponse.QuizResponse

interface QuizRepository {
    suspend fun getQuizzes(): List<Quiz>

    suspend fun getQuizById(id: String): Quiz

    suspend fun addNewQuiz(quiz: QuizResponse)

    suspend fun updateQuiz(quiz: QuizResponse)

    suspend fun deleteQuiz(quizId: String)

    suspend fun addQuizToFavourites(quiz: Quiz)

    suspend fun getFavouriteQuizzes(user: User): List<Quiz>

    suspend fun addQuestionForQuiz(question: QuestionResponse)

    suspend fun updateQuestionForQuiz(question: QuestionResponse)

    suspend fun getQuestionsForQuiz(quizId: String): List<QuestionResponse>

    suspend fun getQuestion(id: String): Question

    suspend fun addAnswerForQuestion(answer: Answer)

    suspend fun editAnswerForQuestion(answer: Answer)

    suspend fun getAnswersForQuestion(questionId: String): List<Answer>

    suspend fun deleteQuestionFromQuiz(id: String)

    suspend fun getDifficultyLevels(): List<DifficultyLevelResponse>

    suspend fun getCategories(): List<CategoryResponse>
}