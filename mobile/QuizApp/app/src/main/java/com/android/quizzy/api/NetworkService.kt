package com.android.quizzy.api

import com.android.quizzy.domain.model.*
import com.android.quizzy.domain.reponse.*
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    @GET("/api/quizzes")
    suspend fun getAllQuizzes(): List<QuizResponse>

    @GET("/api/quizzes/{id}")
    suspend fun getQuizById(@Path("id") id: String): QuizResponse

    @PUT("/api/quizzes")
    suspend fun updateQuiz(@Body quiz: QuizResponse): QuizResponse

    @POST("/api/quizzes")
    suspend fun addQuiz(@Body quiz: QuizResponse)

    @DELETE("/api/quizzes/{id}")
    suspend fun deleteQuiz(@Path("id") id: String)

    @GET("/api/users")
    suspend fun getAllUsers(): List<User>

    @GET("/api/users/name/{userName}")
    suspend fun getUserByUserName(@Path("userName") userName: String): User

    @GET("/api/login")
    suspend fun loginUser(
        @Query("username") userName: String,
        @Query("password") password: String
    ): LoginResponse

    @GET("/api/register")
    suspend fun register(
        username: String,
        password: String,
        name: String,
        email: String,
        avatar: String,
    ): String?

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: String): User

    @GET("/api/register")
    suspend fun createUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("email") email: String,
        @Query("name") name: String,
        @Query("avatar") avatar: String?
    ): RegistryResponse

    @GET("/api/user/settings")
    suspend fun getSettingsForUser(@Query("id") id: Long): UserSettings

    @GET("/api/user/settings/all")
    suspend fun getSettings(): List<UserSettings>

    @PUT("/api/user/settings")
    fun updateSettings( @Body userSettings: UserSettings): Call<UserSettings>
    @GET("/api/register/{id}")
    suspend fun getPassword( @Path("id") id: Int): UserPassword
    @PUT("/api/register")
    fun updatePassword( @Body userPassword: UserPassword): Call<Void>

    @PUT("/api/users")
    fun editUser(@Body user: User): Call<Void>

    @DELETE("/api/users")
    suspend fun deleteUser(@Query("id") id: String)

    @GET("/api/categories")
    suspend fun getCategories(): List<CategoryResponse>

    @GET("/api/answers/{id}")
    suspend fun getAnswersForQuestion(@Path("id") id: String): List<Answer>

    @PUT("/api/answers")
    suspend fun editAnswer(@Body answer: Answer)

    @POST("/api/answers")
    suspend fun createAnswer(@Body answer: Answer)

    @GET("/api/questions")
    suspend fun getQuestions(@Query("quizId") id: String): List<QuestionResponse>

    @GET("/api/questions/{id}")
    suspend fun getQuestionById(@Path("id") id: String): QuestionResponse

    @PUT("/api/questions")
    suspend fun updateQuestion(@Body question: QuestionResponse)

    @DELETE("/api/questions/{id}")
    suspend fun deleteQuestion(@Path("id") id: String)

    @POST("/api/questions")
    suspend fun addQuestion(@Body question: QuestionResponse)

    @GET("/api/difficulty_levels/{id}")
    suspend fun getDifficultyLevel(@Path("id") id: String): DifficultyLevelResponse

    @GET("/api/difficulty_levels")
    suspend fun getAllDifficultyLevels(): List<DifficultyLevelResponse>

    @GET("/api/questions_answers/{id}")
    suspend fun getQuestionWithAnswers(@Path("id") quizId: String): QuestionWithAnswers

    @GET("/api/answers_max_id")
    suspend fun getAnswersMaxId(): Int

    @GET("/api/questions_max_id")
    suspend fun getQuestionsMaxId(): Int

    @GET("/api/quizes_max_id")
    suspend fun getQuizMaxId(): Int

    @GET("/api/ranks")
    suspend fun getRanks(): List<RankResponse>

    @GET("/api/favorites")
    suspend fun getFavouritesQuizzes(
        @Query("userId") userId: Long
    ): List<FavouriteItem>

    @POST("/api/favorites")
    suspend fun addFavouriteQuiz(@Body favouriteItem: FavouriteItem)

    @HTTP(method = "DELETE", path = "/api/favorites", hasBody = true)
    suspend fun deleteFavouriteQuiz(@Body favouriteItem: FavouriteItem)

    @POST("/api/solved_quizzes")
    suspend fun addQuizToSolved(@Body solvedQuiz: SolvedQuizResponse)

    @GET("/api/requests")
    suspend fun getFriendsRequests(
        @Query("fromUser") fromUser: Int? = null,
        @Query("toUser") toUser: Int? = null
    ): List<FriendRequest>

    @POST("/api/requests")
    suspend fun updateFriendsRequests(@Body friendRequest: FriendRequest): FriendRequest
}