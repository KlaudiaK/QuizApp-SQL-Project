package pl.poznan.put.quizzy.favorites.model

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@Table(name="favourites")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(FavoriteItemPK::class)
data class FavoriteItem (
    @Id
    @Column(name = "user_id")
    val userReferenceId: Int,

    @Id
    @Column(name = "quiz_id")
        val quizReferenceId: Int
)