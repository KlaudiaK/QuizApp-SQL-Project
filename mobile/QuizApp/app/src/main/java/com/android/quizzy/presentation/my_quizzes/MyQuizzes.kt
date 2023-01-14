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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.domain.model.Rank
import com.android.quizzy.presentation.destinations.AddNewQuizScreenDestination
import com.android.quizzy.presentation.destinations.QuizDetailsDestination
import com.android.quizzy.presentation.quiz_list.QuizCard
import com.android.quizzy.ui.theme.*
import com.android.quizzy.utils.showToastMessage
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

    val favourites by quizViewModel.getListOfFavouritesQuizzes(8L)
        .collectAsState(initial = listOf())

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        quizViewModel
            .toastMessage
            .collect { message ->
                context.showToastMessage(message)
            }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(
                        AddNewQuizScreenDestination()
                    )
                },
                elevation = FloatingActionButtonDefaults.elevation(12.dp),
                containerColor = pastelBlue,
                shape = CircleShape

            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new Quiz")
            }
        }, floatingActionButtonPosition = FabPosition.End,
        containerColor = black80
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 22.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {


                    Row(
                        Modifier
                            .wrapContentSize()
                            .padding(horizontal = 0.dp, vertical = 0.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Hello,",
                            style = TextStyle(
                                fontSize = 28.sp,
                                color = white20,
                                fontWeight = FontWeight.W300,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.End
                            ),
                        )
                        Text(
                            text = "Katie",
                            style = TextStyle(
                                fontSize = 28.sp,
                                color = white20,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Start,
                            ),
                        )
                    }
                    Text(
                        text = "Glad you're back",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = white20,
                            fontWeight = FontWeight.W100,
                            fontFamily = FontFamily.Monospace,
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }
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

                    Row(modifier = Modifier.fillMaxWidth()) {


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
                        Icon(
                            Icons.TwoTone.WorkspacePremium,
                            contentDescription = null,
                            tint = Rank.values().random().color,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }



            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
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
                    top = 16.dp
                )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.Start,
                content = {

                    uiState.value.quizItems?.let { item ->
                        items(item) { quiz ->
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
                                isLiked = favourites.map { it.quizReferenceId }.contains(quiz.id)
                            )

                        }
                    }
                })
        }
    }
}