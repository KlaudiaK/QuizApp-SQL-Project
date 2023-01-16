package pl.poznan.put.quizzy.login;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pl.poznan.put.quizzy.quizzes.model.db.Quizz
import pl.poznan.put.quizzy.register.model.UserPassword

interface LoginRepository : JpaRepository<UserPassword, Int> {

    @Query("SELECT * FROM users_passwords WHERE username=:username", nativeQuery = true)
    fun getUserPasswordClass(@Param("username") username: String): UserPassword?
}