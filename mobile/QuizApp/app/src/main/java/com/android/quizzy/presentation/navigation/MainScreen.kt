package com.android.quizzy.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.quizzy.presentation.bottom_navigation.ExampleNavigation
import com.android.quizzy.presentation.bottom_navigation.animated.AnimatedBottomNavigation
import com.android.quizzy.viewmodel.UiViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.NavHostEngine

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class
)
@Destination
@Composable
fun MainScreen(

) {

    val navController: NavHostController = rememberAnimatedNavController()

    val navHostEngine: NavHostEngine = rememberAnimatedNavHostEngine()

    val uiViewModel = hiltViewModel<UiViewModel>()

    Scaffold(
        bottomBar = {
            AnimatedBottomNavigation(navController = navController, uiViewModel)

        }
    ) { innerPadding: PaddingValues ->
        ExampleNavigation(
            innerPadding = innerPadding,
            navHostController = navController,
            navHostEngine = navHostEngine,
            viewModel = uiViewModel
        )
    }
}