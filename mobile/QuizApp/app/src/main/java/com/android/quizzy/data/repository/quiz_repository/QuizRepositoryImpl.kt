package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.Question
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): QuizRepository {

    val quizList = mutableListOf<Quiz>().apply {
        for(i in 0..10){
            when{
                i.mod(3) == 0->  add(Quiz.sampleQuiz1.copy(id = i))
                i.mod(3) == 1->  add(Quiz.sampleQuiz2.copy(id = i))
                i.mod(3) == 2->  add(Quiz.sampleQuiz3.copy(id = i))
            }
        }

    }

    var questions =  mutableListOf<Question>().apply {
        for(i in 0..20)
            add(Question.sampleQuestion.copy(questionId = i, content = "What is correct answer for question ${i}?"))
    }

    //TODO Change method to be called on service
    override suspend fun getQuizzes(): List<Quiz> = mutableListOf<Quiz>().apply {
            for(i in 0..10){
                when{
                    i.mod(3) == 0->  add(Quiz.sampleQuiz1.copy(id = i))
                    i.mod(3) == 1->  add(Quiz.sampleQuiz2.copy(id = i))
                    i.mod(3) == 2->  add(Quiz.sampleQuiz3.copy(id = i))
                }
            }
        }
    //networkService.getAllQuizzes()

    override suspend fun getQuizById(id: String): Quiz = networkService.getQuizById(id)

    override suspend fun addNewQuiz(quiz: Quiz) = networkService.addQuiz(quiz)

    override suspend fun updateQuiz(quiz: Quiz) = networkService.updateQuiz(quiz)

    override suspend fun deleteQuiz(quiz: Quiz) = networkService.deleteQuiz(quiz)

    override suspend fun addQuizToFavourites(quiz: Quiz) = networkService.addQuiz(quiz)

    override suspend fun getFavouriteQuizzes(user: User): List<Quiz> = networkService.getAllQuizzes()

    override suspend fun addQuestionForQuiz(question: Question) = networkService.addQuestion(question)

    override suspend fun updateQuestionForQuiz(question: Question) = networkService.updateQuestion(question)

    //TODO Change method to be called on service
    override suspend fun getQuestionsForQuiz(quizId: String) = questions //networkService.getQuestions(quizId)

    override suspend fun addAnswerForQuestion(answer: Answer) = networkService.createAnswer(answer)

    override suspend fun editAnswerForQuestion(answer: Answer) = networkService.editAnswer(answer)

    override suspend  fun getAnswersForQuestion(questionId: String): List<Answer> = networkService.getAnswersForQuestion(questionId)

    override suspend fun deleteQuestionFromQuiz(id: String) = networkService.deleteQuestion(id)

}