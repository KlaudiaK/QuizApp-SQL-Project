package com.android.quizzy.presentation.quiz_list

import androidx.compose.runtime.Composable
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "quiz_list")
@Composable
fun QuizList(
    navigator: DestinationsNavigator,
    UIViewModel: UiViewModel
) {
}