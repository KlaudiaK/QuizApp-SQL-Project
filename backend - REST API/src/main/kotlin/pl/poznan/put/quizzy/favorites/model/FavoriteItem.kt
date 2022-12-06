package pl.poznan.put.quizzy.favorites.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
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