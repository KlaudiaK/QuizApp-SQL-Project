package pl.poznan.put.quizzy.login

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.register.model.UserPassword

@Service
class LoginService(
    private val loginRepository: LoginRepository
) {

    fun validatePassword(username: String, password: String): Boolean? {
       loginRepository.getUserPasswordClass(username)?.let { userData ->
           userData.username?.let {
               return userData.password == password
           }
       }
        return null
    }
}