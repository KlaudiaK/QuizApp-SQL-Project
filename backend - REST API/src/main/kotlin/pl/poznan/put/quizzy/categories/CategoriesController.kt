package pl.poznan.put.quizzy.categories

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.quizzy.categories.model.Category

@RestController
@RequiredArgsConstructor
class CategoriesController(
    private val categoriesService: CategoriesService
) {

    @GetMapping("/api/categories")
    fun getAllCategories(): List<Category> {
        val result = categoriesService.getAllCategories()
        return result
    }
}