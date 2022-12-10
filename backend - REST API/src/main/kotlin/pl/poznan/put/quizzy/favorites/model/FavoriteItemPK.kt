package pl.poznan.put.quizzy.favorites.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class FavoriteItemPK(
    val userReferenceId: Int,
    val quizReferenceId: Int
): Serializable
