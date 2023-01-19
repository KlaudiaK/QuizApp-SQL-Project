package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.api.NetworkService
import com.android.quizzy.domain.model.*
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

    override suspend fun getSettings(): List<UserSettings> {
        return networkService.getSettings()
    }

    override suspend fun updateSettings(userSettings: UserSettings): UserSettings {
        return networkService.updateSettings(userSettings)
    }

    override suspend fun getPassword(id: Int): UserPassword {
        return networkService.getPassword(id)
    }

    override suspend fun updatePassword(userPassword: UserPassword) {
        return networkService.updatePassword(userPassword)
    }

    override suspend fun getFriendsRequests(fromUser: Int?, toUser: Int?): List<FriendRequest> {
        return networkService.getFriendsRequests(fromUser = fromUser, toUser = toUser)
    }

    override suspend fun updateFriendsRequests(friendRequest: FriendRequest): FriendRequest {
        return networkService.updateFriendsRequests(friendRequest)
    }
}