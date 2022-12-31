package pl.poznan.put.quizzy.quizzes.model.db

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import javax.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import pl.poznan.put.quizzy.quizzes.model.PrivacySettings
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    val creationDate: LocalDate? = LocalDate.now(),

    @Column(name= "modification_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    val modificationDate: LocalDate? = LocalDate.now(),

    @Column(name="privacy_settings")
    val privacySettings: String?,//PrivacySettings.PRIVATE.name,

    @Column(name="categories_name")
    val categoryName: String,

    @Column(name="Difficulty_Levels_Difficulty_Levels_ID")
    val difficultyLevelReferenceId: Int,

    @Column(name = "creator_user_id")
    val creatorId: Int
)
