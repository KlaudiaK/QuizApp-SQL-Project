package pl.poznan.put.quizzy.categories.model

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
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Category (

    @Id
    @Column
    val name: String,

    @Column
    val description: String?
)