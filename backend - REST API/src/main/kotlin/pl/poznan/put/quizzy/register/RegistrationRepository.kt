package pl.poznan.put.quizzy.register

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.register.model.UserPassword

@Repository
interface RegistrationRepository: JpaRepository<UserPassword, Long> {

    @Procedure("create_user")
    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        avatar: String,
    )
}