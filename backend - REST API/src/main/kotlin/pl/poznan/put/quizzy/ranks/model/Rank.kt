package pl.poznan.put.quizzy.ranks.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
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
    val minPoints: String,

    @Column(name = "max_points")
    val maxPoints: String,
)
