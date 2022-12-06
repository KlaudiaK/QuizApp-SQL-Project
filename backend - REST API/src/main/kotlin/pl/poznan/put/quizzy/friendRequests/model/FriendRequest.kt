package pl.poznan.put.quizzy.friendRequests.model

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
import java.time.LocalDate

@Data
@Builder
@Table(name="friends requests")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(FriendRequestPK::class)
data class FriendRequest(
    @Id
    @Column(name = "sent")
    val sent: LocalDate = LocalDate.now(),

    @Id
    @Column(name = "from_user")
    val fromUserReferenceId: Int,

    @Id
    @Column(name = "to_user")
    val toUserReferenceId: Int
)
