package com.android.quizzy.presentation.answer

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.android.quizzy.R
import com.android.quizzy.domain.mapToQuestion
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.domain.model.Question
import com.android.quizzy.presentation.destinations.FinalScoreScreenDestination
import com.android.quizzy.presentation.destinations.MyQuizesScreenDestination
import com.android.quizzy.presentation.destinations.WholeAnswerScreenDestination
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuestionWithAnswersState
import com.android.quizzy.viewmodel.QuizDetailsViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun AnswerScreen(
    navigator: DestinationsNavigator,
    answers: List<Answer>, no: Int,
    question: Question,
    quizId: String,
    questionNum: Int,
    submitAnswer: (Answer) -> Unit,
    quizDetailsViewModel: QuizDetailsViewModel,
) {
    var selectedItem by remember { mutableStateOf(-1) }
    val nextNo = no + 1

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp, start = 30.dp, end = 30.dp, bottom = 10.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.Start
    ) {

        Card(
            border = BorderStroke(0.3.dp, black60),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            colors = CardDefaults.cardColors(containerColor = navyDarkBlue60)
        ) {

            Text(
                text = "Question $nextNo", modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                color = black80
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        question.image?.replace("http://", "https://")
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "thumbnail",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(2.dp),
                alpha = 0.95f,
                onLoading = { Log.i("Load", "Loading") },
                onSuccess = { Log.i("Load", "Success") },
                onError = { Log.i("Load", "Error") },
            )
        }

        Text(
            text = question.content, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 14.dp, end = 14.dp, bottom = 18.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = black80
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            itemsIndexed(answers) { index, answer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .height(45.dp)
                        .selectable(
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedItem == index) navyDarkBlue40 else
                            Color.White
                    ),
                    border = BorderStroke(2.dp, navyDarkBlue40)
                ) {

                    Text(
                        answer.content, color = Color.Black, modifier = Modifier
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
                onClick = { navigator.navigateUp() }) {
                Text(text = "Back", color = black80)
            }
            OutlinedButton(
                modifier = Modifier.width(150.dp),
                onClick = {
                    submitAnswer(answers[selectedItem])
                    if (nextNo < questionNum) navigator.navigate(
                        WholeAnswerScreenDestination(
                            no = nextNo,
                            quizId = quizId
                        )
                    )
                    else {
                        navigator.navigate(
                            FinalScoreScreenDestination()
                        )
                        quizDetailsViewModel.addQuizToSolved(quizId.toLong())
                    }
                }, border = BorderStroke(2.dp, brown80),
                enabled = selectedItem != -1
            ) {
                Text(text = "Next", color = black80)
            }
        }

    }
}

@Destination("solve_quiz")
@Composable
fun WholeAnswerScreen(
    navigator: DestinationsNavigator,
    quizDetailsViewModel: QuizDetailsViewModel,
    no: Int,
    quizId: String,
    uiViewModel: UiViewModel
) {
    uiViewModel.onBottomBarVisibilityChange(false)

    LaunchedEffect(Unit) {
        quizDetailsViewModel.getQuestionWithAnswers(quizId)
    }

    val answers = quizDetailsViewModel.questionWithAnswers.value
    var showInfo by remember {
        mutableStateOf(false)
    }
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

    val questionsState = quizDetailsViewModel.questionListState.collectAsState().value
    when (questionsState) {
        is QuestionWithAnswersState.NoResult -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "The author of this quiz hasn't yet added any questions",
                textAlign = TextAlign.Center,
                fontSize = 34.sp, fontWeight = FontWeight.W700, lineHeight = 40.sp
            )
            Button(
                onClick = { navigator.navigate(MyQuizesScreenDestination()) },
                modifier = Modifier
                    .height(50.dp)
                    .width(130.dp)
            ) {
                Text("Finish", fontSize = 20.sp, fontWeight = FontWeight.W500)
            }
        }
        is QuestionWithAnswersState.Success -> answers?.let {
            SolveQuiz(
                composition = composition,
                progress = progress,
                linearProgress = (no + 1) / it.size.toFloat(),
                navigator = navigator,
                no = no,
                question = answers[no].question.mapToQuestion(),
                quizId = quizId,
                answerViewState = answers[no].answers,
                questionNum = answers.size,
                submitAnswer = {
                    quizDetailsViewModel.submitAnswer(
                        question = answers[no].question.mapToQuestion(),
                        it
                    )
                },
                quizDetailsViewModel = quizDetailsViewModel
            )
        }
        else -> {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun SolveQuiz(
    composition: LottieComposition?,
    progress: Float,
    linearProgress: Float,
    answerViewState: List<Answer>,
    navigator: DestinationsNavigator,
    no: Int,
    question: Question,
    quizId: String,
    questionNum: Int,
    submitAnswer: (Answer) -> Unit,
    quizDetailsViewModel: QuizDetailsViewModel,
) {
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
            progress = linearProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 46.dp, vertical = 50.dp),
            trackColor = ecru
        )

        AnswerScreen(
            navigator,
            answerViewState,
            no,
            question,
            quizId,
            questionNum,
            submitAnswer,
            quizDetailsViewModel
        )
    }
}