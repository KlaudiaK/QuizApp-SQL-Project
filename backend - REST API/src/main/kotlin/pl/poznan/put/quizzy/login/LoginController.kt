package pl.poznan.put.quizzy.login

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.quizzy.login.model.LoginResponse

@RestController
@RequiredArgsConstructor
class LoginController(
    private val loginService: LoginService
) {

    @GetMapping("/api/login")
    fun login(@RequestParam username: String, @RequestParam password: String): LoginResponse {
         return loginService.validatePassword(username, password)
    }
}