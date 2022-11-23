package com.android.quizzy.presentation.my_quizzes

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.quizzy.domain.Categories
import com.android.quizzy.domain.Rank
import com.android.quizzy.presentation.destinations.QuizDetailsDestination
import com.android.quizzy.presentation.quiz_list.QuizCard
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuizListViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(route = "home_tab_screen")
@Composable
fun MyQuizesScreen(
    navigator: DestinationsNavigator,
    viewModel: UiViewModel,
    quizViewModel: QuizListViewModel = hiltViewModel(),
) {
    viewModel.onBottomBarVisibilityChange(true)
    val uiState = quizViewModel.uiState
    var url = "https://freesvg.org/img/abstract-user-flat-4.png"

    Scaffold(
        floatingActionButton = {
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
        Column {
            Text(
                text = "Hello username !",
                style = TextStyle(
                    fontSize = 32.sp,
                    color = white20,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    border = BorderStroke(0.3.dp, black60),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .padding(12.dp)
                        .height(60.dp)
                        .width(60.dp),
                    colors = CardDefaults.cardColors(containerColor = green60),
                    shape = CircleShape
                ) {


                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "thumbnail",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1.0f)
                            .padding(2.dp)
                            .clip(CircleShape),
                        alpha = 0.95f,
                        onLoading = { Log.i("Load", "Loading") },
                        onSuccess = { Log.i("Load", "Success") },
                        onError = { Log.i("Load", "Error") },
                    )

                }
                Icon(
                    Icons.TwoTone.WorkspacePremium,
                    contentDescription = null,
                    tint = Rank.values().random().color,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .height(0.2.dp)
                    .background(white20)
            )
            Text(
                text = "YOUR QUIZZES",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = white20,
                    fontWeight = FontWeight.W300,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(
                    start = 8.dp, top = 10.dp
                )
            )

            LazyColumn(
                modifier = Modifier.padding(top = 10.dp),
                content = {

                    uiState.value.quizItems?.let { item ->
                        items(item) { i ->
                            QuizCard(
                                item = i,
                                onClick = {
                                    //navigator.navigate(BookDetailScreenDestination(bookId = book.id))
                                },
                                backgroundColor = Categories.values()
                                    .find { it.name.contentEquals(i.category, true) }?.color
                                    ?: green20
                            )

                        }
                    }


                })
        }
    }
}