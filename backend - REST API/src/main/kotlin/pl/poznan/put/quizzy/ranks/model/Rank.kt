package pl.poznan.put.quizzy.ranks.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@Table(name="ranks")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Rank(
    @Id
    @Column
    val name: String,

    @Column(name = "min_points")
    val minPoints: Int,

    @Column(name = "max_points")
    val maxPoints: Int,
)
