package pl.poznan.put.quizzy.register.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import pl.poznan.put.quizzy.register.model.UserPassword
import java.time.LocalDate
import javax.persistence.*

@Data
@Builder
@Table(name="users_passwords")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class UserPassword (
    @Id
    @Column(name = "id")
    val userReferenceId: Int,

    @Column(name = "password")
    val password: String? = "",

    @Column(name = "username")
    val username: String? = null,

    @Column(name = "last_modified")
    val lastModified: String? = LocalDate.now().toString()
)