package com.android.quizzy.api

import com.android.quizzy.domain.reponse.QuizResponse
import com.android.quizzy.domain.model.*
import com.android.quizzy.domain.reponse.DifficultyLevelResponse
import com.android.quizzy.domain.reponse.QuestionResponse
import retrofit2.http.*

interface NetworkService {

    @GET("/api/quizzes")
    suspend fun getAllQuizzes(): List<QuizResponse>

    @GET("/api/quizzes/{id}")
    suspend fun getQuizById(@Path("id") id: String): QuizResponse

    @PUT("/api/quizzes")
    suspend fun updateQuiz(@Body quiz: Quiz)

    @POST("/api/quizzes")
    suspend fun addQuiz(@Body quiz: QuizResponse)

    //TODO change body to id
    @DELETE("/api/quizzes/{id}")
    suspend fun deleteQuiz(@Path("id") id: String)

    @GET("/api/users")
    suspend fun getAllUsers(): List<User>

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: String): User

    @POST("/api/users")
    fun createUser(@Body user: User)

    @POST("/api/users")
    fun editUser(@Body user: User)

    @DELETE("/api/users")
    suspend fun deleteUser(@Query("id") id: String)

    @GET("/api/categories")
    suspend fun getCategories(): List<Category>

    @GET("/api/answers/{id}")
    suspend fun getAnswersForQuestion(@Path("id") id: String): List<Answer>

    @PUT("/api/answers")
    suspend fun editAnswer(@Body answer: Answer)

    @POST("/api/answer")
    suspend fun createAnswer(@Body answer: Answer)

    @GET("/api/questions/{id}")
    suspend fun getQuestions(@Path("id") id: String): List<QuestionResponse>

    @PUT("/api/questions")
    suspend fun updateQuestion(@Body question: QuestionResponse)

    @DELETE("/api/questions")
    suspend fun deleteQuestion(@Query("id") id: String)

    @POST("/api/questions")
    suspend fun addQuestion(@Body question: QuestionResponse)

    @GET("/api/difficulty_levels/{id}")
    suspend fun getDifficultyLevel(@Path("id") id: String): DifficultyLevelResponse

    @GET("/api/difficulty_levels")
    suspend fun getAllDifficultyLevels(): List<DifficultyLevelResponse>
}