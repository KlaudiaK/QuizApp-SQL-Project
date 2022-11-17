package com.android.quizzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.quizzy.presentation.login.LoginScreen
import com.android.quizzy.ui.theme.QuizzyAppTheme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContent {
            QuizzyAppTheme(
                darkTheme = true,
                dynamicColor = false,
                content = {
                    LoginScreen(navigator = EmptyDestinationsNavigator)
                })
        }

    }

}
