package pl.poznan.put.quizzy.solvedQuizzes.model

import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.time.LocalDate

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class SolvedQuizzPK(
    val userReferenceId: Int,
    val date: LocalDate = LocalDate.now(),
    val quizReferenceId: Int
) : Serializable