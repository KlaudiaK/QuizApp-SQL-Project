package pl.poznan.put.quizzy.register

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.register.model.UserPassword

@Service
class RegistrationService(
    private val registrationRepository: RegistrationRepository
) {
    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        avatar: String?,
    ): String? {
        return registrationRepository.registerUser(username, password, email, name, avatar)
    }

    fun getPassword(id: Int): UserPassword {
        return registrationRepository.getUserPasswordByUserReferenceId(id)
    }
    fun updatePassword(userPassword: UserPassword) {
        registrationRepository.updatePassword(userPassword.password, userPassword.userReferenceId)
    }
}