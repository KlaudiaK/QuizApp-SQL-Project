package pl.poznan.put.quizzy.login

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.register.model.UserPassword

@Service
class LoginService(
    private val loginRepository: LoginRepository
) {

        fun validatePassword(username: String, password: String): Int {
       loginRepository.getUserPasswordClass(username)?.let { userData ->
           userData.username?.let {
               if ( userData.password == password ) {
                   return userData.userReferenceId
               } else {
                   return -1
               }
           }
       }
        return -1
    }
}