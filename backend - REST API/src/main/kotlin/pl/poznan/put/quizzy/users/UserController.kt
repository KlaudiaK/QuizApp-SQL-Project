package pl.poznan.put.quizzy.users

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import pl.poznan.put.quizzy.users.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    @GetMapping("/api/users/id={id}")
    fun getUser(@PathVariable id: Long): User? {
        val result = userService.getUserById(id)
        println(result)
        return result
    }

    @DeleteMapping("/api/users")
    fun deleteUser(@RequestParam id: Long) {
        userService.deleteUser(id)
    }

    @PutMapping("/api/users",
            consumes = [MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    fun putUser(
        @RequestBody user: User
    ): ResponseEntity<HttpStatus> {
        userService.modifyUser(user)
        return ResponseEntity.ok(HttpStatus.CREATED)
    }
    @PostMapping("/api/users",
        consumes = [MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUser(
        @RequestBody user: User
    ): ResponseEntity<HttpStatus> {
        userService.createUser(user)
        return ResponseEntity.ok(HttpStatus.CREATED)
    }
}