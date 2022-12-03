package com.android.quizzy.data.repository.user_repository

import com.android.quizzy.domain.model.User

interface UserRepository {

    fun getUser(id: Int): User
}