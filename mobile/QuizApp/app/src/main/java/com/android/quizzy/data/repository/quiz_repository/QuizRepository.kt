package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.domain.reponse.*

interface QuizRepository {
    suspend fun getQuizzes(): List<Quiz>

    suspend fun getQuizById(id: String): Quiz

    suspend fun addNewQuiz(quiz: QuizResponse)

    suspend fun updateQuiz(quiz: QuizResponse): QuizResponse

    suspend fun deleteQuiz(quizId: String)

    suspend fun addQuizToFavourites(quizId: Long)

    suspend fun getFavouriteQuizzes(userId: Long): List<FavouriteItem>

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

    suspend fun getQuestionWithAnswers(quizId: String) : QuestionWithAnswers

    suspend fun getMaxQuizID(): Int

    suspend fun getMaxQuestionId(): Int

    suspend fun getMaxAnswerId(): Int

    suspend fun getRanks(): List<RankResponse>

    suspend fun deleteQuizFromFavourites(id: Long)

    suspend fun addQuizToSolved(quiz: SolvedQuizResponse)
}