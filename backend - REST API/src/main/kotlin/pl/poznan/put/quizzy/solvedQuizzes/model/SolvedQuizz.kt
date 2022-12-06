package pl.poznan.put.quizzy.solvedQuizzes.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.time.LocalDate

@Data
@Builder
@Table(name="solved quizes")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(SolvedQuizzPK::class)
data class SolvedQuizz (
    @Id
    @Column(name = "user_id")
    val userReferenceId: Int,

    @Id
    @Column(name = "date")
    val date: LocalDate = LocalDate.now(),

    @Id
    @Column(name = "quiz_id")
    val quizReferenceId: Int
): Serializable

