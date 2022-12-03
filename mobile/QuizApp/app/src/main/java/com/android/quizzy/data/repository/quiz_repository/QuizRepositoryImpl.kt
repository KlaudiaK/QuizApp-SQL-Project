package com.android.quizzy.data.repository.quiz_repository

import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(): QuizRepository {
    override fun getQuizzes(): Flow<List<Quiz>> = flow {
        emit(
            mutableListOf<Quiz>().apply {
                for(i in 0..10){
                    when{
                        i.mod(3) == 0->  add(Quiz.sampleQuiz1.copy(id = i))
                        i.mod(3) == 1->  add(Quiz.sampleQuiz2.copy(id = i))
                        i.mod(3) == 2->  add(Quiz.sampleQuiz3.copy(id = i))
                    }
                }

            }
        )
    }

    override fun addNewQuiz(quiz: Quiz) {
        TODO("Not yet implemented")
    }

    override fun deleteQuiz(quiz: Quiz) {
        TODO("Not yet implemented")
    }

    override fun addQuizToFavourites(quiz: Quiz) {
        TODO("Not yet implemented")
    }

    override fun getFavouriteQuizzes(user: User): Flow<List<Quiz>> = flow{
        emit(
            mutableListOf<Quiz>().apply {
                for(i in 11..20)
                    add(Quiz.sampleQuiz.copy(id = i))
            }
        )
    }

}