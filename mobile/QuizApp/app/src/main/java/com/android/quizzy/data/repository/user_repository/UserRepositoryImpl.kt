package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.model.RegistryResponse
import com.android.quizzy.domain.model.User
import com.android.quizzy.domain.model.UserRegister
import retrofit2.Call
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
    ) : UserRepository {
    override suspend fun getUser(id: String): User {
        return networkService.getUserById(id)
    }

    override suspend fun getUsers(): List<User> {
        return networkService.getAllUsers()
    }

    override suspend fun getUserByUserName(userName: String): User? {
        return networkService.getUserByUserName(userName)
    }

    override suspend fun login(userName: String, password: String): Int {
        return networkService.loginUser(userName, password).id
    }

    override suspend fun editUser(user: User) = networkService.editUser(user)

    override suspend fun deleteUser(id: String) = networkService.deleteUser(id)

    override suspend fun createUser(user: UserRegister): RegistryResponse {
        return networkService.createUser(
            username = user.username,
            email = user.email,
            name = user.name,
            password = user.password,
            avatar = user.avatar
        )
    }
}