package pl.poznan.put.quizzy.register

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.userSettings.UserSettingsService
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem

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
        @RequestParam avatar: String,): Boolean {
        val result = registrationService.registerUser(username, password, email, name, avatar)
        result.let {
            return@let
        }
        throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Username or password not found"
        )
    }
}