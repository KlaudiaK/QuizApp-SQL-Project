package com.android.quizzy.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val description: String? = "",
    val icon: ImageVector,
    val color: Color
)


enum class Categories(val icon: ImageVector, val color: Color){
    MOVIE(Icons.TwoTone.MovieFilter, Color(0xF0AF72CF)),
    MUSIC(Icons.TwoTone.MusicNote, Color(0xF072C4CF)),
    SCIENCE(Icons.TwoTone.Science, Color(0xF0729CCF)),
    SPORT(Icons.TwoTone.SportsTennis, Color(0xF072CF74)),
    ANIMALS(Icons.TwoTone.Pets, Color(0xF0CFCA72)),
    ART(Icons.TwoTone.ColorLens, Color(0xF0CF7293)),
    TRAVEL(Icons.TwoTone.TravelExplore,Color(0xF0CF9372)),
    BOOKS(Icons.TwoTone.Book, Color(0xF0A2825B))
}
