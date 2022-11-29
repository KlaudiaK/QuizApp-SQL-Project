package com.android.quizzy.presentation.answer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.android.quizzy.R
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.ui.theme.lightPastelBlue20
import com.android.quizzy.ui.theme.yellowPastel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerScreen(
    navigator: DestinationsNavigator
) {
    var selectedItem by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp, horizontal = 30.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Question ....", modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        )
        Text(
            text = "What is ..... ?", modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(4) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .height(50.dp)
                        .selectable(
                            selected = selectedItem == it.toString(),
                            onClick = { selectedItem = it.toString() }),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedItem == it.toString()) lightPastelBlue20 else
                            Color.White
                    ),
                    border = BorderStroke(2.dp, lightPastelBlue20)
                ) {

                    Text(
                        it.toString(), color = Color.Black, modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )

                }
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            TextButton(
                modifier = Modifier.width(100.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Back")
            }
            OutlinedButton(
                modifier = Modifier.width(150.dp),
                onClick = { /*TODO*/ }, border = BorderStroke(2.dp, yellowPastel)
            ) {
                Text(text = "Next")
            }
        }

    }
}

@Destination
@Composable
fun WholeAnswerScreen(navigator: DestinationsNavigator) {
    var isPlaying by remember {
        mutableStateOf(true)
    }

    var speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.dots)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false

    )

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = black80
    ) {
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.fillMaxSize()
        )
        LinearProgressIndicator(
            progress = 0.7f, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 46.dp, vertical = 50.dp)
        )
        AnswerScreen(navigator)
    }
}