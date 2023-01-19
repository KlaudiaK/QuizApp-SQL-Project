package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.domain.model.*
import retrofit2.Call
import retrofit2.http.*

interface UserRepository {

    suspend fun getUser(id: String): User
    suspend fun getUsers(): List<User>
    suspend fun getUserByUserName(userName: String): User?
    suspend fun login(userName: String, password: String): Int?

    suspend fun editUser(user: User)

    suspend fun deleteUser(id: String)

    suspend fun createUser(user: UserRegister): RegistryResponse

    suspend fun getSettings(): List<UserSettings>
    suspend fun updateSettings( userSettings: UserSettings): UserSettings
    suspend fun getPassword( id: Int): UserPassword
    suspend fun updatePassword(  userPassword: UserPassword)
}