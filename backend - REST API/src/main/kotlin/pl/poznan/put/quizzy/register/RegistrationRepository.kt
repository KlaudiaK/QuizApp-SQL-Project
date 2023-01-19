package pl.poznan.put.quizzy.register

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.register.model.UserPassword
import java.time.LocalDate

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

    fun getUserPasswordByUserReferenceId(id: Int): UserPassword

    @Modifying
    @Query("update users_passwords set password = :password, last_modified = :last_modified where id = :userId", nativeQuery = true)
    fun updatePassword(@Param("password")password: String?, @Param("userId")id: Int, @Param("last_modified") lastModified: LocalDate = LocalDate.now())
}