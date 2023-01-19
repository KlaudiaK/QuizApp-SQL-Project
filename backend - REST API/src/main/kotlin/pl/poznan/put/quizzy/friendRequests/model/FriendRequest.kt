package pl.poznan.put.quizzy.friendRequests.model

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
import java.time.LocalDate

@Data
@Builder
@Table(name="friends_requests")
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
    val fromUserReferenceId: Int? = null,

    @Id
    @Column(name = "to_user")
    val toUserReferenceId: Int? = null,

    @Id
    @Column(name = "status")
    val status: String = "Sent"
)
