package com.android.quizzy.presentation.final_score

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.quizzy.R
import com.android.quizzy.presentation.destinations.MyQuizesScreenDestination
import com.android.quizzy.viewmodel.QuizDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FinalScoreScreen(
    navigator: DestinationsNavigator,
    quizDetailsViewModel: QuizDetailsViewModel,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.star)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = false
    )

    val score by quizDetailsViewModel.getFinalScore().collectAsState(initial = 0)

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LottieAnimation(
            composition,
            progress,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            "You scored: $score / ${quizDetailsViewModel.uiState.value.questions.size}",
            fontSize = 34.sp
        )
        Button(onClick = {
            navigator.navigate(MyQuizesScreenDestination())
            quizDetailsViewModel.clearUserAnswers()
        }, modifier = Modifier
            .padding(vertical = 40.dp)
            .height(50.dp)
            .width(130.dp)) {
            Text("Finish", fontSize = 20.sp, fontWeight = FontWeight.W500)
        }
    }
}