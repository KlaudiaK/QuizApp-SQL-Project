package com.android.quizzy.presentation.new_question

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.AddCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.android.quizzy.R
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuestionViewModel
import com.android.quizzy.viewmodel.QuizViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun NewQuestion(
    navigator: DestinationsNavigator,
    questionViewModel: QuestionViewModel = hiltViewModel(),
    uizViewModel: QuizViewModel = hiltViewModel(),
    questionId: Long? = null,
    isInEditMode: Boolean = false,
    quizId: Long
) {
    if (isInEditMode) questionId?.let { questionViewModel.getQuestion(it) }
    val uiState by remember { questionViewModel.uiState }
    val inputErrors = questionViewModel.inputErrors
    val scrollState = rememberScrollState()

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        }
    }) {

        Surface(color = black80) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 36.dp)

            ) {

                Text(
                    text = if (isInEditMode) "Edit question" else "Add new question",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W400,
                    color = pastelWhite,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 16.dp, end = 20.dp)
                        .align(Alignment.Start)
                )

                //QuestionImage(modifier = Modifier.height(130.dp))
                Card(
                    border = BorderStroke(0.3.dp, black60),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(vertical = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = green60)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                uiState.image.replace("http://", "https://")
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = "thumbnail",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        alpha = 0.95f,
                        onLoading = { Log.i("Load", "Loading") },
                        onSuccess = { Log.i("Load", "Success") },
                        onError = { Log.i("Load", "Error") },
                    )
                }
                OutlinedTextField(
                    value = uiState.image,
                    onValueChange = { questionViewModel.onImageChanged(it) },
                    label = { Text("Image URL") },
                    placeholder = { Text("Paste here image URL to display it above :)") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = lightGreen20,
                        textColor = white20,
                        focusedBorderColor = darkGreen80,
                        focusedLabelColor = darkGreen80,
                        unfocusedLabelColor = darkGreen80,
                    ),
                    modifier = Modifier
                        .heightIn(max = 60.dp)
                        .fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            ContextCompat.startActivity(
                                context,
                                intent,
                                null
                            )
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    },
                    isError = inputErrors.value.imageErrorId != null,
                    supportingText = {
                        inputErrors.value.imageErrorId?.let {
                            Text(stringResource(id = it))
                        }
                    }
                )
                OutlinedTextField(
                    value = uiState.question,
                    onValueChange = {
                        if (it.length < 100) questionViewModel.onQuestionChanged(
                            it
                        )
                    },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    label = { Text("Question") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = lightGreen20,
                        textColor = white20,
                        focusedBorderColor = darkGreen80,
                        focusedLabelColor = darkGreen80,
                        unfocusedLabelColor = darkGreen80,
                    ),
                    isError = inputErrors.value.questionErrorId != null,
                    supportingText = {
                        inputErrors.value.questionErrorId?.let {
                            Text(stringResource(id = it))
                        }
                    }
                )
                questionViewModel.list.forEachIndexed { index, answer ->

                    QuestionItem(
                        text = answer.content,
                        onItemSelected = {
                            questionViewModel.onCorrectAnswerChanged(index)
                        },
                        isSelected = answer.isCorrect,
                        onValueChanged = { questionViewModel.onAnswerChangedChanged(it, index) },
                    )
                }

                if (inputErrors.value.answersErrorId.any { it != null }) {
                    Text(text = stringResource(id = inputErrors.value.answersErrorId.find { it != null }
                        ?: R.string.answer_cannot_be_empty),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.error
                        ))
                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (!isInEditMode) {
                        OutlinedButton(
                            onClick = { questionViewModel.onSaveClicked(quizId) },
                            modifier = Modifier
                                .width(180.dp)
                                .wrapContentHeight()
                                .padding(vertical = 24.dp)
                        ) {
                            Text(
                                text = "Save",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.W300,
                                color = lightPastelBlue20
                            )
                        }
                        TextButton(onClick = { questionViewModel.onAddNewQuestionClicked() }) {
                            Text(
                                text = "Add another",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W300,
                                color = orange20
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = {
                                questionId?.let {
                                    questionViewModel.onEditClicked(it)
                                }
                            },
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            Text(
                                text = "Save",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.W300,
                                color = lightPastelBlue20
                            )
                        }
                    }

                }
            }
        }

    }


}


@Composable
fun QuestionImage(modifier: Modifier) {
    var selectImages by remember { mutableStateOf(listOf<Uri>()) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            selectImages = it
        }


    Card(
        border = BorderStroke(0.3.dp, black60),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = green60)
    ) {
        if (selectImages.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(selectImages[0]),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                    }
            )

        } else {

            IconButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    Icons.TwoTone.AddCircleOutline,
                    contentDescription = null,
                    tint = pastelGreen
                )
            }

            Text(text = "You can add a picture to the question :)", color = Color.White)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionItem(
    text: String,
    onItemSelected: (String) -> Unit,
    isSelected: Boolean,
    onValueChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(0.5.dp, pastelWhite),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isSelected, onCheckedChange = { onItemSelected(text) })
            BasicTextField(
                value = text, onValueChange = { if (it.length < 100) onValueChanged(it) },
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 6.dp)
                    .align(Alignment.CenterVertically),
                textStyle = TextStyle(color = white20),
            )
        }

    }
}