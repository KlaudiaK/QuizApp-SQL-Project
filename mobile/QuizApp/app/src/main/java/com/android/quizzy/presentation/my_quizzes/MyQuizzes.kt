package com.android.quizzy.presentation.my_quizzes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.android.quizzy.presentation.destinations.QuizDetailsDestination
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(route = "home_tab_screen")
@Composable
fun MyQuizesScreen(
    navigator: DestinationsNavigator,
    viewModel: UiViewModel
) {
    viewModel.onBottomBarVisibilityChange(true)
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navigator.navigate(
                QuizDetailsDestination
            )
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new Quiz")
        }
    }, floatingActionButtonPosition = FabPosition.End,
        containerColor = black80
    ) {

    }

}