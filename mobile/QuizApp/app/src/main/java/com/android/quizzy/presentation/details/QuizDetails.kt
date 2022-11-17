package com.android.quizzy.presentation.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.viewmodel.QuizViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "quiz_details")
@Composable
fun QuizDetails(navigator: DestinationsNavigator, viewModel: UiViewModel, quizViewModel: QuizViewModel = hiltViewModel()) {}