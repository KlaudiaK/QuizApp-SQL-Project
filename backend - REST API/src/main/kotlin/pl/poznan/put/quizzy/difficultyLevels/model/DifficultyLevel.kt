package pl.poznan.put.quizzy.difficultyLevels.model

import javax.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@Table(name="difficulty_levels")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class DifficultyLevel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficulty_levels_id")
    val id: Long,

    @Column
    val name: String,

    @Column
    val description: String?,

    @Column
    val stars: Int
)
