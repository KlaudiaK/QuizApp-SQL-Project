package pl.poznan.put.quizzy.categories.model

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