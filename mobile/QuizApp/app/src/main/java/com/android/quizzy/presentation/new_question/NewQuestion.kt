package com.android.quizzy.presentation.new_question

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.AddCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuestionViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun NewQuestion(
    navigator: DestinationsNavigator,
    questionViewModel: QuestionViewModel = hiltViewModel()
) {
    val uiState = questionViewModel.uiState
    val scrollState = rememberScrollState()
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
                    text = "Add new question",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W400,
                    color = pastelWhite,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 16.dp, end = 20.dp)
                        .align(Alignment.Start)
                )

                QuestionImage(modifier = Modifier.height(130.dp))

                OutlinedTextField(
                    value = uiState.value.question,
                    onValueChange = {
                        if (it.length < 100) questionViewModel.onQuestionChanged(
                            it
                        )
                    },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        ,
                    label = { Text("Question") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = lightGreen20,
                        textColor = white20,
                        focusedBorderColor = darkGreen80,
                        focusedLabelColor = darkGreen80,
                        unfocusedLabelColor = darkGreen80,
                    )
                )
                questionViewModel.list.forEachIndexed { index, answer ->

                    QuestionItem(
                        text = answer.content,
                        onItemSelected = {
                            questionViewModel.onCorrectAnswerChanged(index)
                        },
                        isSelected = answer.isCorrect,
                        onValueChanged = { questionViewModel.onAnswerChangedChanged(it, index) })
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { /*TODO*/ },
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

                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Add another",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W300,
                            color = orange20
                        )
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