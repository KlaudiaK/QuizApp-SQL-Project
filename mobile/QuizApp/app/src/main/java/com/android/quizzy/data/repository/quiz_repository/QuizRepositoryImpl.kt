package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.mapToQuestion
import com.android.quizzy.domain.model.*
import com.android.quizzy.domain.reponse.*
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): QuizRepository {

    //TODO Change method to be called on service
    override suspend fun getQuizzes(): List<Quiz> = networkService.getAllQuizzes().map {
        with(it) {
            Quiz(
                id = id,
                title = name,
                author = creatorId.toString(),
                description = description,
                sharing = PrivacySetting.valueOf(privacySettings),
                image = image,
                category = categoryName,
                difficulty = networkService.getDifficultyLevel(difficultyLevelReferenceId.toString()).name
            )
        }

    }

    override suspend fun getQuizById(id: String): Quiz = with(networkService.getQuizById(id)){
        Quiz(
            id = id.toLong(),
            title = name,
            author = creatorId.toString(),
            description = description,
            sharing = PrivacySetting.PRIVATE,
            image = image,
            category = categoryName,
            points = points,
            difficulty = networkService.getDifficultyLevel(difficultyLevelReferenceId.toString()).name
        )
    }


    override suspend fun addNewQuiz(quiz: QuizResponse) {
        val quizModel = QuizResponse(
            name ="Quiz naukowy",
            description = "Testowy quiz naukowy",
            categoryName = "Science",
            difficultyLevelReferenceId = 2,
            points = 100,
            id = 12,
            image = "https://img.freepik.com/free-vector/colorful-science-education-background_23-2148490697.jpg?w=2000",
            modificationDate = LocalDate.now().toString(),
            creationDate = LocalDate.now().toString(),
            creatorId = 1,
            likes = 10,
            privacySettings = "PUBLIC"
        )
        networkService.addQuiz(quiz)
    }

    override suspend fun updateQuiz(quiz: QuizResponse) = networkService.updateQuiz(quiz)

    override suspend fun deleteQuiz(id: String) = networkService.deleteQuiz(id)

    override suspend fun addQuizToFavourites(quizId: Long) = networkService.addFavouriteQuiz(
        FavouriteItem(2, quizId)
    )

    override suspend fun getFavouriteQuizzes(userId: Long) = networkService.getFavouritesQuizzes(userId)

    override suspend fun addQuestionForQuiz(question: QuestionResponse) = networkService.addQuestion(question)

    override suspend fun updateQuestionForQuiz(question: QuestionResponse) = networkService.updateQuestion(question)

    override suspend fun getQuestionsForQuiz(quizId: String) = networkService.getQuestions(quizId)

    override suspend fun getQuestion(id: String) = networkService.getQuestionById(id).mapToQuestion()

    override suspend fun addAnswerForQuestion(answer: Answer) = networkService.createAnswer(answer)

    override suspend fun editAnswerForQuestion(answer: Answer) = networkService.editAnswer(answer)

    override suspend  fun getAnswersForQuestion(questionId: String): List<Answer> = networkService.getAnswersForQuestion(questionId)

    override suspend fun deleteQuestionFromQuiz(id: String) = networkService.deleteQuestion(id)

    override suspend fun getDifficultyLevels() = networkService.getAllDifficultyLevels()

    override suspend fun getCategories(): List<CategoryResponse> = networkService.getCategories()

    override suspend fun getQuestionWithAnswers(quizId: String): QuestionWithAnswers = networkService.getQuestionWithAnswers(quizId)

    override suspend fun getMaxQuizID(): Int = networkService.getQuizMaxId()

    override suspend fun getMaxQuestionId(): Int = networkService.getQuestionsMaxId()

    override suspend fun getMaxAnswerId(): Int = networkService.getAnswersMaxId()

    override suspend fun getRanks(): List<RankResponse> = networkService.getRanks()

    override suspend fun deleteQuizFromFavourites(id: Long) = networkService.deleteFavouriteQuiz(FavouriteItem(
        2, id
    ))

    override suspend fun addQuizToSolved(quiz: SolvedQuizResponse) = networkService.addQuizToSolved(quiz)
}