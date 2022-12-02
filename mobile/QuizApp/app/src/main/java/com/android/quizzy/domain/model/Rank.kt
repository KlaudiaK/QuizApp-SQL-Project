package com.android.quizzy.domain.model

import androidx.compose.ui.graphics.Color
import com.android.quizzy.ui.theme.bronze
import com.android.quizzy.ui.theme.metalicGold
import com.android.quizzy.ui.theme.platinium
import com.android.quizzy.ui.theme.silver

enum class Rank(val color: Color) {
    BRONZE(bronze), SILVER(silver), GOLD(metalicGold), PLATINIUM(platinium)
}