package pl.poznan.put.quizzy.answers.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy

@Data
@Builder
@Table(name="answers")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Answer(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column
    val content: String,

    @Column(name="is_correct")
    val isCorrect: Boolean,

    @Column(name = "questions_quizes_id")
    val questionReference: Int
)
