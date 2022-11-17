package com.android.quizzy.presentation.details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.AddCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.android.quizzy.domain.Categories
import com.android.quizzy.domain.SharingOption
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuizViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

val textFieldModifier = Modifier
    .fillMaxWidth()
    .padding(vertical = 8.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun quizDetailsTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    unfocusedBorderColor = pastelViolet,
    textColor = pastelViolet,
    focusedBorderColor = pastelViolet,
    focusedLabelColor = pastelViolet,
    unfocusedLabelColor = pastelViolet,
    focusedTrailingIconColor = pastelViolet,
    unfocusedTrailingIconColor = pastelViolet
)

@OptIn(ExperimentalMaterial3Api::class)
@Destination(route = "add_new_quiz")
@Composable
fun QuizDetails(navigator: DestinationsNavigator, viewModel: UiViewModel, quizViewModel: QuizViewModel = hiltViewModel()) {
    val uiState = quizViewModel.uiState
    var title by remember{ mutableStateOf(TextFieldValue("")) }

    viewModel.onBottomBarVisibilityChange(false)
    val categories = Categories.values().map {
        it.name
    }
    val sharingOptions = SharingOption.values().map {
        it.name
    }
    viewModel.onBottomBarVisibilityChange(false)
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
                    .padding(horizontal = 24.dp, vertical = 36.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterStart)
                    ) {
                        Text(
                            text = "Create new quiz",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.W600,
                            color = pastelWhite,
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 16.dp, end = 20.dp)
                                .align(Alignment.Start)
                        )
                        OutlinedTextField(
                            value = uiState.value.title,
                            onValueChange = { if(it.length < 40) quizViewModel.onTitleChanged(it)} ,
                            modifier = Modifier
                                .heightIn(max = 80.dp)
                                .width(170.dp),
                            label = { Text("Title") },
                            colors = quizDetailsTextFieldColors(),
                            maxLines = 2,

                            )
                    }

                    QuizImage(modifier = Modifier.align(Alignment.TopEnd))
                }

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = textFieldModifier.height(130.dp),
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Description") },
                    colors = quizDetailsTextFieldColors(),

                    )
                CategoryDropDown(optionList = categories, label = "Category")
                CategoryDropDown(optionList = sharingOptions, label = "Difficulty level")
                OutlinedTextField(
                    value = "", onValueChange = {}, modifier = textFieldModifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Points") },
                    colors = quizDetailsTextFieldColors(),
                )
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(180.dp)
                        .padding(vertical = 24.dp)
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W500,
                        color = lightPastelBlue20
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(optionList: List<String>, label: String) {

    var mExpanded by remember { mutableStateOf(false) }


    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(0.dp) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(textFieldModifier) {

        OutlinedTextField(

            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->

                    mTextFieldSize = coordinates.size.width.dp
                },
            label = { Text(label) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            },
            readOnly = true,
            colors = quizDetailsTextFieldColors()
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize })
        ) {
            optionList.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        mSelectedText = label
                        mExpanded = false
                    },
                    text = { Text(text = label, color = darkPastelBlue) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizImage(modifier: Modifier) {
    var selectImages by remember { mutableStateOf(listOf<Uri>()) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            selectImages = it
        }


    Box(
        modifier
            .height(120.dp)
            .width(130.dp)
            .padding(start = 12.dp, top = 12.dp)

    ) {


        Card(
            // colors = CardDefaults.cardColors(containerColor = pastelPink),
            border = BorderStroke(0.3.dp, black60),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .align(Alignment.TopStart)
            // horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (selectImages.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(selectImages[0]),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        // .padding(16.dp, 8.dp)
                        .size(100.dp)
                        .clickable {

                        }
                )

            }


        }

        IconButton(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
                .height(30.dp)
                .align(Alignment.BottomEnd)
            //.padding(10.dp)

        ) {
            Icon(Icons.TwoTone.AddCircleOutline, contentDescription = null, tint = yellowPastel)
        }
    }
}
