package pl.poznan.put.quizzy.users

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.users.model.User

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User>? {
        return userRepository.findAll().toList()
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User with id: $id not found"
            )
        }
    }
    fun getUserByUserName(userName: String): User? {
        return userRepository.findUserByUserName(userName)
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