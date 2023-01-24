package com.android.quizzy.presentation.bottom_navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.quizzy.presentation.NavGraphs
import com.android.quizzy.presentation.add_new_quiz.AddNewQuizScreen
import com.android.quizzy.presentation.answer.WholeAnswerScreen
import com.android.quizzy.presentation.categories.CategoriesScreen
import com.android.quizzy.presentation.destinations.*
import com.android.quizzy.presentation.details.QuizDetails
import com.android.quizzy.presentation.final_score.FinalScoreScreen
import com.android.quizzy.presentation.login.LoginScreen
import com.android.quizzy.presentation.my_quizzes.MyQuizesScreen
import com.android.quizzy.presentation.new_question.NewQuestion
import com.android.quizzy.presentation.question_list.QuestionList
import com.android.quizzy.presentation.quiz_list.QuizList
import com.android.quizzy.presentation.registration_form.OnboardingViewModel
import com.android.quizzy.viewmodel.QuizDetailsViewModel
import com.android.quizzy.viewmodel.QuizViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.spec.NavHostEngine

@Composable
fun ExampleNavigation(
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    navHostEngine: NavHostEngine,
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    viewModel: UiViewModel
) {

    val quizDetailsViewModel = hiltViewModel<QuizDetailsViewModel>()

    DestinationsNavHost(
        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        navGraph = NavGraphs.root,
        engine = navHostEngine,
        navController = navHostController,
        startRoute = onboardingViewModel.getStartRoute(),


        ) {
        composable(QuizListDestination) {
            QuizList(
                uiViewModel = viewModel,
                navigator = this.destinationsNavigator,
                category = navArgs.category
            )
        }
        composable(CategoriesScreenDestination) {
            CategoriesScreen(
                uiViewModel = viewModel,
                navigator = this.destinationsNavigator
            )
        }
        composable(AddNewQuizScreenDestination) {
            AddNewQuizScreen(
                navigator = this.destinationsNavigator,
                viewModel = viewModel,
                quizViewModel = hiltViewModel(),
                isEditMode = navArgs.isEditMode,
                quizToEditID = navArgs.quizToEditID
            )
        }
        composable(MyQuizesScreenDestination) {
            MyQuizesScreen(navigator = this.destinationsNavigator, viewModel = viewModel)
        }
        composable(LoginScreenDestination) {
            LoginScreen(navigator = this.destinationsNavigator, uiViewModel = viewModel)
        }
        composable(QuestionListDestination) {
            QuestionList(
                navigator = this.destinationsNavigator,
                uiViewModel = viewModel,
                quizId = navArgs.quizId
            )
        }
        composable(WholeAnswerScreenDestination) {
            WholeAnswerScreen(
                navigator = this.destinationsNavigator,
                uiViewModel = viewModel,
                no = navArgs.no,
                quizDetailsViewModel = quizDetailsViewModel,
                quizId = navArgs.quizId
            )
        }
        composable(QuizDetailsDestination) {
            QuizDetails(
                navigator = this.destinationsNavigator,
                quizDetailsViewModel = quizDetailsViewModel,
                quizId = navArgs.quizId,
                uiViewModel = viewModel,
                profileViewModel = hiltViewModel()
            )
        }
        composable(NewQuestionDestination) {
            NewQuestion(
                navigator = this.destinationsNavigator,
                questionId = navArgs.questionId,
                isInEditMode = navArgs.isInEditMode,
                quizId = navArgs.quizId
            )
        }
        composable(FinalScoreScreenDestination) {
            FinalScoreScreen(navigator = this.destinationsNavigator, quizDetailsViewModel = quizDetailsViewModel)
        }
    }

}


