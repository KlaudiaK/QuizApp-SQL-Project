package pl.poznan.put.quizzy.quizzes.model

import javax.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDate

@Data
@Builder
@Table(name="quizes")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Quizz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id : Long,

    @Column
    val name: String,

    @Column
    val description: String?,

    @Column
    val image: String?,

    @Column
    val points: Int,

    @Column
    val likes: Int = 0,

    @Column(name = "creation_date")
    val creationDate: String = LocalDate.now().toString(),

    @Column(name= "modification_date")
    val modificationDate: String = LocalDate.now().toString(),

    @Column(name="privacy_settings")
    val privacySettings: String = PrivacySettings.PRIVATE.name,

    @Column(name="categories_name")
    val categoryName: String,

    @Column(name="Difficulty_Levels_Difficulty_Levels_ID")
    val difficultyLevelReferenceId: Int,

    @Column(name = "creator_user_id")
    val creatorId: Int
)
