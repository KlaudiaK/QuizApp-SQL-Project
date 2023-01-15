package pl.poznan.put.quizzy.login

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.register.model.UserPassword

@Service
class LoginService(
    private val loginRepository: LoginRepository
) {

    fun validatePassword(username: String, password: String): Boolean {
        return loginRepository.getUserPasswordClass(username).password == password
    }
}