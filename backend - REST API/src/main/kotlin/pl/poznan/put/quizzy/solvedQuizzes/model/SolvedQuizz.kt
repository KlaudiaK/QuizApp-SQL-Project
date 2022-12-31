package pl.poznan.put.quizzy.solvedQuizzes.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.time.LocalDate

@Data
@Builder
@Table(name="solved_quizes")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(SolvedQuizzPK::class)
data class SolvedQuizz (
    @Id
    @Column(name = "user_id")
    val userReferenceId: Long,

    @Id
    @Column(name = "date")
    val date: LocalDate = LocalDate.now(),

    @Id
    @Column(name = "quiz_id")
    val quizReferenceId: Long
): Serializable

