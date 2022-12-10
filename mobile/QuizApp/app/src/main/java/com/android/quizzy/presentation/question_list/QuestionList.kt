package com.android.quizzy.presentation.question_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.presentation.destinations.NewQuestionDestination
import com.android.quizzy.presentation.expanded_card.ExpandableCard
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.viewmodel.QuestionListViewModel
import com.android.quizzy.viewmodel.QuizListViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Destination(route = "question_list")
@Composable
fun QuestionList(
    quizId: String,
    navigator: DestinationsNavigator,
    quizViewModel: QuizListViewModel = hiltViewModel(),
    questionListViewModel: QuestionListViewModel = hiltViewModel(),
    uiViewModel: UiViewModel
) {

    uiViewModel.onBottomBarVisibilityChange(false)
    val uiState = quizViewModel.uiState
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = uiState.value.isRefreshing)
    val scrollState = rememberLazyListState()

    questionListViewModel.getQuestions(quizId)
    val questionList = questionListViewModel.questions//.uiState.value.questions
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        }
    }) {
        Surface(color = black80) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, end = 24.dp, top = 20.dp)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .fillMaxSize(), state = scrollState,
                    content = {

                        items(questionListViewModel.questions) { question ->
                            ExpandableCard(
                                title = question.content,
                                content =
                                {
                                    QuestionCardItem(
                                        answers = Answer.listOfAnswers,
                                        onEditClicked = { navigator.navigate(NewQuestionDestination) },
                                        onDeleteClicked = {
                                            questionListViewModel.deleteQuestion(
                                                question.questionId
                                            )
                                        },
                                        questionId = question.questionId
                                    )
                                }

                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                    })
            }
        }
    }
}