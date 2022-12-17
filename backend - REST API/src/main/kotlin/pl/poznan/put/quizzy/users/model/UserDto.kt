package pl.poznan.put.quizzy.users.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
data class UserDto (
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("username")
    val userName: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("avatar")
    val avatar: String?,

    @JsonProperty("total_points")
    val totalPoints: Int?,

    @JsonProperty("solved_quizes")
    val solvedQuizes: Int?,

    @JsonProperty("created_quizes")
    val createdQuizes: Int?,

    @JsonProperty("rank")
    val rank: String?
    )