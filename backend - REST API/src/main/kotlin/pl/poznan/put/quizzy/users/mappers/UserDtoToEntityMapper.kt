package pl.poznan.put.quizzy.users.mappers

import pl.poznan.put.quizzy.users.model.User
import pl.poznan.put.quizzy.users.model.UserDto

fun UserDto.toEntity() : User {
    return User(
        id = id,
        userName = userName,
        email = email,
        name = name,
        avatar = avatar,
        totalPoints = totalPoints,
        solvedQuizes = solvedQuizes,
        createdQuizes = createdQuizes,
        rank = rank
    )
}