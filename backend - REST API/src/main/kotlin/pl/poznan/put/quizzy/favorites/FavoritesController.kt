package pl.poznan.put.quizzy.favorites

import com.azure.core.annotation.Delete
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.favorites.model.FavoriteItem

@RestController
@RequiredArgsConstructor
class FavoritesController(
    private val favoritesService: FavoritesService
) {

    @GetMapping("/api/favorites")
    fun getFavorites(@RequestParam userId: Long?, @RequestParam quizId: Long?): List<FavoriteItem> {
        userId?.let {
            return favoritesService.getFavoritesForUser(userId)
        }
        quizId?.let {
            return favoritesService.getFavoritesForQuiz(quizId)
        }
        return listOf()
    }

    @PostMapping("/api/favorites")
    fun addFavorite(@RequestBody favoriteItem: FavoriteItem): FavoriteItem {
        return favoritesService.addFavorite(favoriteItem)
    }

    @DeleteMapping("/api/favorites")
    fun deleteFavorite(@RequestBody favoriteItem: FavoriteItem) {
        return favoritesService.deleteFavorite(favoriteItem)
    }
}