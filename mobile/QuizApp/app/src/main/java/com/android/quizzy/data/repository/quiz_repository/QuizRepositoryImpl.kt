package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.reponse.QuizResponse
import com.android.quizzy.domain.model.*
import com.android.quizzy.domain.reponse.QuestionResponse
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
                image = image
            )
        }

    }

    override suspend fun getQuizById(id: String): Quiz = with(networkService.getQuizById(id)){
        Quiz(
            id = id.toInt(),
            title = name,
            author = creatorId.toString(),
            description = description,
            sharing = PrivacySetting.PRIVATE,
            image = image,
            category = categoryName,
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

    override suspend fun updateQuiz(quiz: Quiz) = networkService.updateQuiz(quiz)

    override suspend fun deleteQuiz(id: String) = networkService.deleteQuiz(id)

    override suspend fun addQuizToFavourites(quiz: Quiz) {}//= networkService.addQuiz(quiz)

    override suspend fun getFavouriteQuizzes(user: User): List<Quiz> = listOf()//networkService.getAllQuizzes()

    override suspend fun addQuestionForQuiz(question: QuestionResponse) = networkService.addQuestion(question)

    override suspend fun updateQuestionForQuiz(question: QuestionResponse) = networkService.updateQuestion(question)

    //TODO Change method to be called on service
    override suspend fun getQuestionsForQuiz(quizId: String) = networkService.getQuestions(quizId)//questions //

    override suspend fun addAnswerForQuestion(answer: Answer) = networkService.createAnswer(answer)

    override suspend fun editAnswerForQuestion(answer: Answer) = networkService.editAnswer(answer)

    override suspend  fun getAnswersForQuestion(questionId: String): List<Answer> = networkService.getAnswersForQuestion(questionId) //Answer.listOfAnswers//

    override suspend fun deleteQuestionFromQuiz(id: String) = networkService.deleteQuestion(id)

    override suspend fun getDifficultyLevels() = networkService.getAllDifficultyLevels()
}