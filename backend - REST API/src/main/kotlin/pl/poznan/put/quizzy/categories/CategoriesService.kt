package pl.poznan.put.quizzy.categories

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.categories.model.Category

@Service
class CategoriesService(
    private val categoriesRepository: CategoriesRepository
) {
    fun getAllCategories(): List<Category> {
        return categoriesRepository.findAll()
    }
}