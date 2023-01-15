package pl.poznan.put.quizzy.login

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.quizzes.model.api.QuizzResponse
import pl.poznan.put.quizzy.quizzes.model.mapper.mapToApiModel

@RestController
@RequiredArgsConstructor
class LoginController(
    private val loginService: LoginService
) {

    @GetMapping("/api/login")
    fun login(@RequestParam username: String, @RequestParam password: String): Boolean {
        val result = loginService.validatePassword(username, password)
        result.let {
            return@let
        }
        throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Username or password not found"
        )
    }
}