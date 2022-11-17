package com.android.quizzy.presentation.my_quizzes

import androidx.compose.runtime.Composable
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "home_tab_screen")
@Composable
fun MyQuizesScreen(
    navigator: DestinationsNavigator,
    viewModel: UiViewModel
) {}