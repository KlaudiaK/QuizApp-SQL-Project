package com.android.quizzy.presentation.categories

import com.android.quizzy.domain.Category
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination(route = "community_quizes")
fun CategoriesScreen(categories: List<Category>? = listOf(), navigator: DestinationsNavigator, uiViewModel: UiViewModel){
    uiViewModel.onBottomBarVisibilityChange(true)
}
