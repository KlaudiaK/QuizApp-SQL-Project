package pl.poznan.put.quizzy.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.users.model.User

@Repository
interface UserRepository: JpaRepository<User, Long>