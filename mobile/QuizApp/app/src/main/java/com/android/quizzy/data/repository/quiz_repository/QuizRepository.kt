package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.domain.model.User
import com.android.quizzy.domain.reponse.DifficultyLevelResponse
import com.android.quizzy.domain.reponse.QuestionResponse
import com.android.quizzy.domain.reponse.QuizResponse

interface QuizRepository {
    suspend fun getQuizzes(): List<Quiz>

    suspend fun getQuizById(id: String): Quiz

    suspend fun addNewQuiz(quiz: QuizResponse)

    suspend fun updateQuiz(quiz: Quiz)

    suspend fun deleteQuiz(quizId: String)

    suspend fun addQuizToFavourites(quiz: Quiz)

    suspend fun getFavouriteQuizzes(user: User): List<Quiz>

    suspend fun addQuestionForQuiz(question: QuestionResponse)

    suspend fun updateQuestionForQuiz(question: QuestionResponse)

    suspend fun getQuestionsForQuiz(quizId: String): List<QuestionResponse>

    suspend fun addAnswerForQuestion(answer: Answer)

    suspend fun editAnswerForQuestion(answer: Answer)

    suspend fun getAnswersForQuestion(questionId: String): List<Answer>

    suspend fun deleteQuestionFromQuiz(id: String)

    suspend fun getDifficultyLevels(): List<DifficultyLevelResponse>
}