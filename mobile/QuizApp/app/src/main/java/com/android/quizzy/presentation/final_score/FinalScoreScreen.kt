package com.android.quizzy.presentation.final_score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.android.quizzy.presentation.destinations.MyQuizesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FinalScoreScreen(
    navigator: DestinationsNavigator,
    score:Int? = 0
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        Text("You scored: $score", fontSize = 34.sp)
        Button(onClick = { navigator.navigate(MyQuizesScreenDestination()) }) {
            Text("Finish")
        }
    }
}