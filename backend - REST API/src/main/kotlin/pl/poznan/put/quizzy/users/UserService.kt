package pl.poznan.put.quizzy.users

import jakarta.annotation.PostConstruct
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.users.model.User

@Service
@RequiredArgsConstructor
class UserService constructor(
    private val userRepository: UserRepository
) {
    fun createUser(
        id: Int,
        userName: String,
        email: String,
        name: String,
        avatar: String,
        totalPoints: Int,
        solvedQuizes: Int,
        createdQuizes: Int,
        ranksMinPoints: Int,
        ranksMaxPoints: Int
    ) {
        val user = User(
            id,
            userName,
            email,
            name,
            avatar,
            totalPoints,
            solvedQuizes,
            createdQuizes,
            ranksMinPoints,
            ranksMaxPoints
        )

        userRepository.save(user)
    }

    fun getAllUsers() : List<User>? {
        return userRepository.findAll()
    }

    fun getUserById(id: Long) : User? {
        return userRepository.findById(id).get()
    }

}