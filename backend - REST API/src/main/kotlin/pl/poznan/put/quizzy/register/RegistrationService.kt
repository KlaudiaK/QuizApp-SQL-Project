package pl.poznan.put.quizzy.register

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem

@Service
class RegistrationService(
    private val registrationRepository: RegistrationRepository
) {
    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        avatar: String,
    ) {
        return registrationRepository.registerUser(username, password, email, name, avatar)
    }
}