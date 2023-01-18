package pl.poznan.put.quizzy.register

import lombok.RequiredArgsConstructor
import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.register.model.RegistryResponse
import pl.poznan.put.quizzy.register.model.UserPassword
import pl.poznan.put.quizzy.userSettings.UserSettingsService
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem
import java.sql.SQLException

@RestController
@RequiredArgsConstructor
class RegistrationController(
    private val registrationService: RegistrationService
) {
    @GetMapping("/api/register")
    fun register(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam email: String,
        @RequestParam name: String,
        @RequestParam avatar: String?) : RegistryResponse? {
        try {
            registrationService.registerUser(username, password, email, name, avatar)?.let {
                return RegistryResponse(it)
            }
        } catch (e: Exception) {
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Couldn't create a user"
            )
        }
        return null
    }
}