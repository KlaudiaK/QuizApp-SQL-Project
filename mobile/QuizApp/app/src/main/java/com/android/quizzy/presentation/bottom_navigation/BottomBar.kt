package com.android.quizzy.presentation.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.quizzy.presentation.NavGraphs
import com.android.quizzy.presentation.categories.CategoriesScreen
import com.android.quizzy.presentation.destinations.*
import com.android.quizzy.presentation.details.AddNewQuizScreen
import com.android.quizzy.presentation.login.LoginScreen
import com.android.quizzy.presentation.my_quizzes.MyQuizesScreen
import com.android.quizzy.presentation.quiz_list.QuizList
import com.android.quizzy.presentation.registration_form.OnboardingViewModel
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
    onboardingViewModel: OnboardingViewModel = hiltViewModel<OnboardingViewModel>(),
    viewModel: UiViewModel
) {


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
            AddNewQuizScreen(navigator = this.destinationsNavigator, viewModel = viewModel, quizViewModel = hiltViewModel<QuizViewModel>())
        }
        composable(MyQuizesScreenDestination) {
            MyQuizesScreen(navigator = this.destinationsNavigator, viewModel = viewModel)
        }
        composable(LoginScreenDestination) {
            LoginScreen(navigator = this.destinationsNavigator, uiViewModel = viewModel)
        }
    }

}


@Composable
fun AppBottomNavigation(
    navController: NavController,
    //bottomBarVisibility: MutableState<Boolean>,
    UIviewModel: UiViewModel = hiltViewModel<UiViewModel>()
) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Quizzes,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val bottomBarVisibility by UIviewModel.visibleHolder.observeAsState(false)

    val currentRoute = navController.currentBackStackEntry?.destination?.route
    if (currentRoute == "login") {
        UIviewModel.onBottomBarVisibilityChange(false)
    } else {
        UIviewModel.onBottomBarVisibilityChange(true)
    }
    AnimatedVisibility(
        visible = bottomBarVisibility,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {

            BottomNavigation(
                modifier = Modifier
                    .clip(RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)),
                backgroundColor = Color.White,
                contentColor = Color.White
            ) {

                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(item.icon, contentDescription = item.title)
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 9.sp
                            )
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Black.copy(0.4f),
                        alwaysShowLabel = true,
                        //selected = currentRoute == item.screen_route,
                        selected = currentDestination?.hierarchy?.any { it.route == item.screen_route } == true,
                        onClick = {
                            navController.navigate(item.screen_route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}

