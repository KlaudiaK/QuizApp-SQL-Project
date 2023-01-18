package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.domain.model.RegistryResponse
import com.android.quizzy.domain.model.User
import com.android.quizzy.domain.model.UserRegister
import retrofit2.Call

interface UserRepository {

    suspend fun getUser(id: String): User
    suspend fun getUsers(): List<User>
    suspend fun getUserByUserName(userName: String): User?
    suspend fun login(userName: String, password: String): Int?

    suspend fun editUser(user: User)

    suspend fun deleteUser(id: String)

    suspend fun createUser(user: UserRegister): RegistryResponse
}