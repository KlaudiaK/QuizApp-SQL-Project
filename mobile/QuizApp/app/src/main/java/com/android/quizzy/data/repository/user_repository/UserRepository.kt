package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.utils.Resource

interface UserRepository {

    suspend fun checkIfUserEmailExistsInDB(
        email: String
    ): Resource<Boolean>

    suspend fun checkIfUsernameExistsInDB(
        email: String
    ): Resource<Boolean>
}