package pl.poznan.put.quizzy.friendRequests.model

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
data class FriendRequestPK(
    val sent: LocalDate = LocalDate.now(),
    val fromUserReferenceId: Int,
    val toUserReferenceId: Int
): Serializable