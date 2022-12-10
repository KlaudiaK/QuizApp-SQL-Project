package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : UserRepository {

    //TODO Change method to be called on service
    override suspend fun getUser(id: Int): User = User(
        username = "Katiexxx123",
        firstname = "Katie",
        email = "katie.inetresting@mail.com"
    )

    override suspend fun editUser(user: User) = networkService.editUser(user)

    override suspend fun deleteUser(id: String) = networkService.deleteUser(id)

    override suspend fun createUser(user: User) = networkService.createUser(user)
}