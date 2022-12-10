package pl.poznan.put.quizzy.users.mappers

import pl.poznan.put.quizzy.users.model.User
import pl.poznan.put.quizzy.users.model.UserDto

fun User.toDto() : UserDto {
    return UserDto(
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