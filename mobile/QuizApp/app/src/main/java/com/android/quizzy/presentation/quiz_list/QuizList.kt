package com.android.quizzy.presentation.quiz_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.presentation.components.SegmentedControl
import com.android.quizzy.presentation.destinations.QuizDetailsDestination
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.ui.theme.green20
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

    var selectedType by remember { mutableStateOf(0) }

    val favourites by quizViewModel.favouritesQuizzes.collectAsState(initial = listOf())

    val quizes by quizViewModel.quizzes.collectAsState()
    var filteredItems by mutableStateOf(quizes.filter { it.sharing?.name == "PUBLIC" })
    val listItems = remember { mutableStateListOf<Quiz>() }

    category?.let {
        if (!it.equals(Categories.OTHER.name, ignoreCase = true)) filteredItems =
            filteredItems.filter { it.category.equals(category, ignoreCase = true) }
    }

    LaunchedEffect(filteredItems) {
        listItems.clear()
        listItems.addAll(filteredItems)
    }

    LaunchedEffect(selectedType) {
        quizViewModel.getListOfFavouritesQuizzes(uiState.value.userId.toLong())
        when (selectedType) {
            0 -> {
                var filteredList = quizes.filter { it.sharing?.name == "PUBLIC" }
                category?.let {
                    if (!it.equals(Categories.OTHER.name, ignoreCase = true)) filteredList =
                        filteredList.filter { it.category.equals(category, ignoreCase = true) }
                }
                listItems.clear()
                listItems.addAll(filteredList)

            }
            1 -> {
                var filteredList = quizes.filter { it.sharing?.name == "FRIENDS" }
                category?.let {
                    if (!it.equals(Categories.OTHER.name, ignoreCase = true)) filteredList =
                        filteredList.filter { it.category.equals(category, ignoreCase = true) }
                }
                listItems.clear()
                listItems.addAll(filteredList)
            }
        }
    }

    Scaffold(containerColor = black80) {

        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SegmentedControl(
                items = listOf("Community", "Friends"), onItemSelection = {
                    selectedType = it
                }, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 12.dp), useFixedWidth = true, itemWidth = 140.dp
            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {}
            ) {
                LazyColumn(
                    modifier = Modifier.padding(top = 60.dp),
                    content = {

                        items(listItems) { quiz ->
                            QuizCard(
                                item = quiz,
                                onClick = {
                                    navigator.navigate(QuizDetailsDestination(quizId = quiz.id))
                                },
                                backgroundColor = Categories.values()
                                    .find { it.name.contentEquals(quiz.category, true) }?.color
                                    ?: green20,
                                onLikeClicked = { id ->
                                    quizViewModel.addQuizToFavourites(id)
                                },
                                onDislikeClicked = { quizViewModel.deleteQuizFromFavourites(it) },
                                isLiked = favourites.map { it.quizReferenceId }
                                    .contains(quiz.id)
                            )
                        }

                    })
            }
        }
    }
}