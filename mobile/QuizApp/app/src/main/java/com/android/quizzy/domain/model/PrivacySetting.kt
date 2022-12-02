package com.android.quizzy.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Public
import androidx.compose.ui.graphics.vector.ImageVector

enum class PrivacySetting(val icon: ImageVector) {
    PRIVATE(Icons.Outlined.Lock), PUBLIC(Icons.Outlined.Public), FRIENDS(Icons.Outlined.Group)
}