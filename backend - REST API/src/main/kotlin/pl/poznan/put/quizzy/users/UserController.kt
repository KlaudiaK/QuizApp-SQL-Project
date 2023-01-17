package pl.poznan.put.quizzy.users

import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.users.model.User

@RestController
@RequiredArgsConstructor
class UserController(
   private val userService: UserService
) {
    @GetMapping("/api/users")
    fun getAllUsers(): List<User>? {
        return userService.getAllUsers()
    }

    @GetMapping("/api/users/{id}")
    fun getUser(@PathVariable id: Long): User? {
        return userService.getUserById(id)
    }
    @GetMapping("/api/users/name/{userName}")
    fun getUserByUserName(@PathVariable userName: String): User? {
        return userService.getUserByUserName(userName)
    }

    @DeleteMapping("/api/users/{id}")
    fun deleteUser(@PathVariable("id") id: Long) {
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