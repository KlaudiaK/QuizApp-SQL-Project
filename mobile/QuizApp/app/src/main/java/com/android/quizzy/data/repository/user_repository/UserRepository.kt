package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.domain.model.*
import retrofit2.Call

interface UserRepository {

    suspend fun getUser(id: String): User
    suspend fun getUsers(): List<User>
    suspend fun getUserByUserName(userName: String): User?
    suspend fun login(userName: String, password: String): Int?

    fun editUser(user: User): Call<Void>

    suspend fun deleteUser(id: String)

    suspend fun createUser(user: UserRegister): RegistryResponse

    suspend fun getSettings(): List<UserSettings>
    fun updateSettings( userSettings: UserSettings): Call<UserSettings>
    suspend fun getPassword( id: Int): UserPassword
    fun updatePassword(  userPassword: UserPassword): Call<Void>

    suspend fun getFriendsRequests(fromUser: Int? = null, toUser: Int? = null): List<FriendRequest>

    suspend fun updateFriendsRequests(friendRequest: FriendRequest) : FriendRequest
}