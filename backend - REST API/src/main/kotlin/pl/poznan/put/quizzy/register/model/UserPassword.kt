package pl.poznan.put.quizzy.register.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDate

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
    val username: String? = "",

    @Column(name = "last_modified")
    val last_modified: LocalDate? = LocalDate.now()
)