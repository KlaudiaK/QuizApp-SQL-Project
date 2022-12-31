package com.android.quizzy.domain.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.ui.graphics.Color
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.domain.model.Category
import com.android.quizzy.domain.reponse.CategoryResponse

fun Category.mapToCategoryResponse(): CategoryResponse =
    with(this){
        CategoryResponse(
            name,
            description
        )
    }


fun CategoryResponse.mapToCategory(): Category =
    with(this){
        Category(
            name,
            description,
            Categories.values().find { it.name.equals(name, ignoreCase = true) }?.icon ?: Icons.Outlined.Category,
            Categories.values().find { it.name.equals(name, ignoreCase = true) }?.color ?: Color(
                0xFFCCB7E6
            )
        )
    }