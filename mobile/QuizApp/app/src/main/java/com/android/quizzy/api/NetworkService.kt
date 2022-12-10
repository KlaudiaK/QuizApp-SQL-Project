package com.android.quizzy.api

import com.android.quizzy.domain.model.*
import retrofit2.http.*

interface NetworkService {

    @GET("/api/quizzes")
    suspend fun getAllQuizzes(): List<Quiz>

    @GET("/api/quizzes/{id}")
    suspend fun getQuizById(@Path("id") id: String): Quiz

    @PUT("/api/quizzes")
    suspend fun updateQuiz(@Body quiz: Quiz)

    @POST("/api/quizzes")
    suspend fun addQuiz(@Body quiz: Quiz)

    //TODO change body to id
    @DELETE("/api/quizzes")
    suspend fun deleteQuiz(@Body quiz: Quiz)

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

    @GET("/api/answers")
    suspend fun getAnswersForQuestion(@Query("questionId") id: String): List<Answer>

    @PUT("/api/answers")
    suspend fun editAnswer(@Body answer: Answer)

    @POST("/api/answer")
    suspend fun createAnswer(@Body answer: Answer)

    @GET("/api/questions")
    suspend fun getQuestions(@Query("quizId") id: String): List<Question>

    @PUT("/api/questions")
    suspend fun updateQuestion(@Body question: Question)

    @DELETE("/api/questions")
    suspend fun deleteQuestion(@Query("id") id: String)

    @POST("/api/questions")
    suspend fun addQuestion(@Body question: Question)
}