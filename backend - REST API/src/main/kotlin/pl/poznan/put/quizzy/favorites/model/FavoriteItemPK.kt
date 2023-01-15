package pl.poznan.put.quizzy.favorites.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import javax.persistence.Embeddable

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
data class FavoriteItemPK(
    var userReferenceId: Long,
    var quizReferenceId: Long
): Serializable
