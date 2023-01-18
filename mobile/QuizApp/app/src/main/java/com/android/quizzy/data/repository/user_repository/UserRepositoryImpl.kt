package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
    ) : UserRepository {
    override suspend fun getUser(id: String): User {
        return networkService.getUserById(id)
    }

    override suspend fun getUserByUserName(userName: String): User? {
        return networkService.getUserByUserName(userName)
    }

    override suspend fun login(userName: String, password: String): Int {
        return networkService.loginUser(userName, password).id
    }

    override suspend fun editUser(user: User) = networkService.editUser(user)

    override suspend fun deleteUser(id: String) = networkService.deleteUser(id)

    override suspend fun createUser(user: User) = networkService.createUser(user)
}