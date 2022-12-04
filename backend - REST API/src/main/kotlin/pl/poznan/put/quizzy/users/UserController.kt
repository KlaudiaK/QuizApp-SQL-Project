package pl.poznan.put.quizzy.users

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.quizzy.users.model.User

@RestController
@RequiredArgsConstructor
class UserController @Autowired constructor(
   private val userService: UserService
) {
    @GetMapping("/api/users")
    fun getAllUsers(): List<User>? {
        val result = userService.getAllUsers()
        println(result)
        return result
    }
}