package pl.poznan.put.quizzy.questions.model.db

import javax.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDate

@Data
@Builder
@Table(name="questions")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Question(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    val content: String,

    @Column
    val image: String?,

    @Column(name = "creation_date")
    val creationDate: LocalDate = LocalDate.now(),

    @Column(name= "modification_date")
    val modificationDate: LocalDate = LocalDate.now(),

    @Column(name = "quizes_id")
    val quizReferenceId: Int
)
