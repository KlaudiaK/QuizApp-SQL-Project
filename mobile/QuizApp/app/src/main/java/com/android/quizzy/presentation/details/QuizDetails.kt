package com.android.quizzy.presentation.details

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Stairs
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.quizzy.R
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.presentation.destinations.AddNewQuizScreenDestination
import com.android.quizzy.presentation.destinations.WholeAnswerScreenDestination
import com.android.quizzy.ui.theme.*
import com.android.quizzy.utils.quizDetailsChipModifier
import com.android.quizzy.viewmodel.ProfileViewModel
import com.android.quizzy.viewmodel.QuizDetailsViewModel
import com.android.quizzy.viewmodel.QuizViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Destination("details")
@Composable
fun QuizDetails(
    uiViewModel: UiViewModel,
    navigator: DestinationsNavigator,
    quizViewModel: QuizViewModel = hiltViewModel(),
    quizDetailsViewModel: QuizDetailsViewModel,
    profileViewModel: ProfileViewModel,
    quizId: Long
) {
    val uiState = quizViewModel.uiState
    val scrollState = rememberScrollState()
    val scrollUpState = uiViewModel.scrollUp.observeAsState()
    // viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
    var user by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true) {
        uiViewModel.onBottomBarVisibilityChange(false)
        quizDetailsViewModel.getQuizDetails(quizId.toString())
        user = quizDetailsViewModel.uiState.value.username.toString()
    }
    val uiDetailsState = quizDetailsViewModel.uiState
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            MotionAppBar(lazyListState, uiDetailsState.value.quiz?.image ?: "", onDeleteClick = {
                quizDetailsViewModel.deleteQuiz(
                    quizId.toString()
                )
                navigator.navigateUp()
            },
            onEditClicked = {
                            navigator.navigate(AddNewQuizScreenDestination(quizToEditID = quizId, isEditMode = true))
            },
            isAuthorized = quizDetailsViewModel.getAuthorization(),
            username = user.toString()
            )
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(top = 200.dp),
            lazyListState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(1) {
                Row(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(top = 150.dp)
                        .wrapContentHeight()
                        .background(backgroundColor),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Chip(
                        onClick = {},
                        colors = ChipDefaults.outlinedChipColors(
                            backgroundColor = black80.copy(
                                alpha = 0.8F
                            )
                        ),
                        border = BorderStroke(0.7.dp, pastelWhite20),
                        modifier = quizDetailsChipModifier
                    ) {
                        Icon(
                            Icons.Filled.Favorite, contentDescription = null, tint = Color.Red,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                        )
                        Text(text = uiDetailsState.value.quiz?.likes.toString(), fontSize = 16.sp)
                    }
                    Chip(
                        onClick = {},
                        colors = ChipDefaults.outlinedChipColors(
                            backgroundColor = black80.copy(
                                alpha = 0.8F
                            )
                        ),
                        border = BorderStroke(0.7.dp, pastelWhite20),
                        modifier = quizDetailsChipModifier
                    ) {
                        Icon(
                            Icons.Outlined.Stairs,
                            contentDescription = null,
                            tint = quizDetailsViewModel.getDifficultyColor(
                                uiDetailsState.value.quiz?.difficulty
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                        )
                        Text(
                            text = uiDetailsState.value.quiz?.difficulty ?: "Easy",
                            fontSize = 16.sp
                        )
                    }

                    Chip(
                        onClick = {},
                        colors = ChipDefaults.outlinedChipColors(
                            backgroundColor = black80.copy(
                                alpha = 0.8F
                            )
                        ),
                        border = BorderStroke(0.7.dp, pastelWhite20),
                        modifier = quizDetailsChipModifier
                    ) {
                        Icon(
                            Categories.SCIENCE.icon,
                            contentDescription = null,
                            tint = Categories.SCIENCE.color,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                        )
                        uiDetailsState.value.quiz?.category?.let { category ->
                            Text(
                                text = category,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Text(
                    uiDetailsState.value.quiz?.title ?: "",
                    fontSize = 36.sp, color = pastelWhite,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                )
                Text(
                    uiDetailsState.value.quiz?.description ?: "",
                    fontSize = 16.sp, color = pastelWhite, fontWeight = FontWeight.W200,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(200.dp))
                Button(
                    onClick = {
                        quizDetailsViewModel.getQuestions(quizId.toString())
                        navigator.navigate(WholeAnswerScreenDestination(no = 0, quizId = quizId.toString()))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .height(60.dp)
                        .width(150.dp)
                ) {
                    Text("Solve", fontSize = 20.sp)
                }
            }
        }

    }
}


@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionAppBar(
    lazyScrollState: LazyListState, imageUrl: String,
    onDeleteClick: () -> Unit,
    onEditClicked: () -> Unit,
    isAuthorized: Boolean,
    username: String
) {

    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }

    val progress by animateFloatAsState(
        targetValue = if (lazyScrollState.firstVisibleItemScrollOffset <= 50) 0f else 1f,
        tween(300)
    )

    val motionHeight by animateDpAsState(
        targetValue = if (lazyScrollState.firstVisibleItemScrollOffset <= 50) 200.dp else 50.dp,
        tween(300)
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .height(motionHeight)
        ) {

            val boxProperties = motionProperties(id = "box")
            val roundedShape = RoundedCornerShape(
                bottomStart = boxProperties.value.int("roundValue").dp,
                bottomEnd = boxProperties.value.int("roundValue").dp
            )

            Box(
                modifier = Modifier
                    .layoutId("box")
                    .clip(roundedShape)
                    .background(
                        backgroundColor
                    )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()

                        .clip(RoundedCornerShape(bottomStartPercent = 12, bottomEndPercent = 12)),
                    alpha = 0.95f,
                    onLoading = { Log.i("Load", "Loading") },
                    onSuccess = { Log.i("Load", "Success") },
                    onError = { Log.i("Load", "Error") },
                )
                if (isAuthorized) {
                    IconButton(
                        onClick = { onDeleteClick() },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = redLight
                        )
                    }
                    IconButton(onClick = { onEditClicked() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 30.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            tint = lightPastelBlue40
                        )
                    }
                }

            }

            Text(
                text = username,
                fontSize = 24.sp,
                fontWeight = if (progress == 1f) FontWeight.Light else FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.layoutId("user_name")
            )

            AsyncImage( model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
                placeholder = painterResource(R.drawable.profile_pic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .layoutId("user_image"))
        }
    }
}