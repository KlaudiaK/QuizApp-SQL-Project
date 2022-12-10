package pl.poznan.put.quizzy.categories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.categories.model.Category

@Repository
interface CategoriesRepository: JpaRepository<Category, Long>