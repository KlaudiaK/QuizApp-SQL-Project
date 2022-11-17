package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.utils.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun checkIfUserEmailExistsInDB(email: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfUsernameExistsInDB(email: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

}