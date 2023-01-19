package com.android.quizzy.domain.model

import androidx.compose.ui.graphics.Color
import com.android.quizzy.ui.theme.easyGreen
import com.android.quizzy.ui.theme.hardRed
import com.android.quizzy.ui.theme.mediumYellow

enum class DifficultyLevel(val stars: Int, val color: Color, val description: String) {
    EASY(stars = 1, color = easyGreen, description = "Łatwy"),
    MEDIUM(stars = 2, color = mediumYellow, description = "Średni"),
    HARD(stars = 3, color = hardRed, "Trudny")
}