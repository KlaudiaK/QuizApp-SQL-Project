package com.android.quizzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.android.quizzy.ui.theme.QuizzyAppTheme
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
                    Column(Modifier.fillMaxSize()) {
                        Text(text = "Sample text")
                    }
                })
        }

    }

}
