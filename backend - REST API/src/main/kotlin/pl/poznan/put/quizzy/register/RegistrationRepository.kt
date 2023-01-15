package pl.poznan.put.quizzy.register

import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.register.model.UserPassword

@Repository
interface RegistrationRepository: JpaRepository<UserPassword, Long> {

    @Query("SELECT (CAST(record AS VARCHAR)) from create_user(:vusername, :vpassword, :vemail, :vname, :vavatar) as (record record);", nativeQuery = true)
    fun registerUser(
        @Param("vusername") username: String,
        @Param("vpassword") password: String,
        @Param("vemail") email: String,
        @Param("vname") name: String,
        @Param("vavatar") avatar: String? = null,
    ) : String?
}