package pl.poznan.put.quizzy.favorites

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.favorites.model.FavoriteItem

@Service
class FavoritesService(
    private val favoritesRepository: FavoritesRepository
) {
    fun getFavoritesForUser(userId: Long): List<FavoriteItem> {
        return favoritesRepository.findAll().filter { it.userReferenceId.toLong() == userId }
    }
    fun getFavoritesForQuiz(quizId: Long): List<FavoriteItem> {
        return favoritesRepository.findAll().filter { it.quizReferenceId.toLong() == quizId }
    }

    fun addFavorite(favoriteItem: FavoriteItem): FavoriteItem {
        return favoritesRepository.save(favoriteItem)
    }

    fun deleteFavorite(favoriteItem: FavoriteItem) {
        return favoritesRepository.delete(favoriteItem)
    }
}