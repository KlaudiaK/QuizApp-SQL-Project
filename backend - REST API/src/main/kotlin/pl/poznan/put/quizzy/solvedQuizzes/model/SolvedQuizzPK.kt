package pl.poznan.put.quizzy.solvedQuizzes.model

import javax.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Embeddable

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
data class SolvedQuizzPK(
    var userReferenceId: Int,
    var date: LocalDate = LocalDate.now(),
    var quizReferenceId: Int
) : Serializable