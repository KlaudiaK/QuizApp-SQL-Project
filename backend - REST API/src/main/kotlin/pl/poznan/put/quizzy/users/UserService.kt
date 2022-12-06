package pl.poznan.put.quizzy.users

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.users.model.User
import kotlin.jvm.optionals.getOrNull

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers() : List<User>? {
        return userRepository.findAll().toList()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getUserById(id: Long) : User? {
        return userRepository.findById(id).getOrNull()
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun modifyUser(user: User) {
        userRepository.save(user)
    }
    fun createUser(user: User) {
        userRepository.save(user)
    }

}