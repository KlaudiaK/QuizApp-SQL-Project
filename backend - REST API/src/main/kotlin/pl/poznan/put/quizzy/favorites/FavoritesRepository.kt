package pl.poznan.put.quizzy.favorites

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.favorites.model.FavoriteItem
import pl.poznan.put.quizzy.favorites.model.FavoriteItemPK

@Repository
interface FavoritesRepository: JpaRepository<FavoriteItem, FavoriteItemPK>