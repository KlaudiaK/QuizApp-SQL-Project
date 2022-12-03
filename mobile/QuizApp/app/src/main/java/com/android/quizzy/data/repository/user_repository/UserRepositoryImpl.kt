package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUser(id: Int): User = User(
        username = "Katiexxx123",
        firstname = "Katie",
        email = "katie.inetresting@mail.com"
    )
}