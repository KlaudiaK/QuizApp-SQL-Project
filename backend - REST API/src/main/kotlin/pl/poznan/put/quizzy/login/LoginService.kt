package pl.poznan.put.quizzy.login

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.login.model.LoginResponse
import pl.poznan.put.quizzy.register.model.UserPassword

@Service
class LoginService(
    private val loginRepository: LoginRepository
) {

        fun validatePassword(username: String, password: String): LoginResponse {
       loginRepository.getUserPasswordClass(username)?.let { userData ->
           userData.username?.let {
               if ( userData.password == password ) {
                   return  LoginResponse(userData.userReferenceId.toInt())
               } else {
                   return LoginResponse(-1)
               }
           }
       }
        return LoginResponse(-1)
    }
}