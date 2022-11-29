package com.android.quizzy.presentation.quiz_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.domain.Quiz
import com.android.quizzy.presentation.components.SegmentedControl
import com.android.quizzy.presentation.destinations.QuizDetailsDestination
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.viewmodel.QuizListViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(route = "quiz_list")
@Composable
fun QuizList(
    category: String? = null,
    navigator: DestinationsNavigator,
    quizViewModel: QuizListViewModel = hiltViewModel(),
    uiViewModel: UiViewModel
) {

    uiViewModel.onBottomBarVisibilityChange(false)
    val uiState = quizViewModel.uiState
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = uiState.value.isRefreshing)

    Scaffold(containerColor = black80) {


        Box(Modifier.fillMaxSize().padding(16.dp)) {
            SegmentedControl(
                items = listOf("Community", "Friends"), onItemSelection = {}, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 12.dp), useFixedWidth = true, itemWidth = 140.dp
            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { }
            ) {

                val quiz = Quiz.sampleQuiz
                LazyColumn(
                    modifier = Modifier.padding(top = 60.dp),
                    content = {
                        uiState.value.quizItems?.let {
                            items(it.size) { i ->
                                QuizCard(
                                    item = quiz,
                                    onClick = {
                                        navigator.navigate(QuizDetailsDestination)
                                    },
                                )

                            }
                        }
                    })
            }
        }
    }
}