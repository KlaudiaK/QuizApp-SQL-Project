package com.android.quizzy.di

import com.android.quizzy.data.repository.quiz_repository.QuizRepository
import com.android.quizzy.data.repository.quiz_repository.QuizRepositoryImpl
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.data.repository.user_repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuizRepository() : QuizRepository = QuizRepositoryImpl()

    @Provides
    @Singleton
    fun provideUserRepository() : UserRepository = UserRepositoryImpl()
}