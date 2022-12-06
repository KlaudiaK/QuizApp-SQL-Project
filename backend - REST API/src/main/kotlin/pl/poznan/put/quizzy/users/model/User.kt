package pl.poznan.put.quizzy.users.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    val id: Int,

    @Column(name="username")
    val userName: String,

    @Column
    val email: String,

    @Column
    val name: String,

    @Column
    val avatar: String?,

    @Column(name = "total_points")
    val totalPoints: Int?,

    @Column(name = "solved_quizes")
    val solvedQuizes: Int?,

    @Column(name = "created_quizes")
    val createdQuizes: Int?,

    @Column(name = "rank")
    val rank: String?
)