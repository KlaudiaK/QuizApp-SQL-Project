package com.android.quizzy.presentation.details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.AddCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.android.quizzy.presentation.destinations.NewQuestionDestination
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Destination(route = "add_new_quiz")
@Composable
fun QuizDetails(
    navigator: DestinationsNavigator,
    viewModel: UiViewModel,
    quizViewModel: QuizViewModel = hiltViewModel()
) {
    val uiState = quizViewModel.uiState
    var title by remember { mutableStateOf(TextFieldValue("")) }

    viewModel.onBottomBarVisibilityChange(false)
    val categories = Categories.values().map {
        it.name
    }
    val sharingOptions = SharingOption.values().map {
        it.name
    }

    val scrollState = rememberScrollState()

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
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 36.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
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
                            onValueChange = { if (it.length < 40) quizViewModel.onTitleChanged(it) },
                            modifier = Modifier
                                .heightIn(max = 80.dp)
                                .width(170.dp),
                            label = { Text("Title") },
                            colors = quizDetailsTextFieldColors(),
                            maxLines = 2,

                            )
                    }

                    QuizImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .height(120.dp)
                            .width(130.dp)
                    )
                }

                OutlinedTextField(
                    value = uiState.value.description,
                    onValueChange = { quizViewModel.onDescriptionChanged(it) },
                    modifier = textFieldModifier.height(130.dp),
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Description") },
                    colors = quizDetailsTextFieldColors(),

                    )
                CategoryDropDown(
                    optionList = categories,
                    label = "Category",
                    selectedOption = uiState.value.category
                ) { quizViewModel.onCategoryChanged(it) }
                CategoryDropDown(
                    optionList = sharingOptions,
                    label = "Sharing option",
                    selectedOption = uiState.value.sharingOption
                ) { quizViewModel.onSharingOptionChanged(it) }
                OutlinedTextField(
                    value = "", onValueChange = {}, modifier = textFieldModifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Points") },
                    colors = quizDetailsTextFieldColors(),
                )




                ChipGroup(
                    list = SharingOption.values().toList(),
                    onSelectedChanged = { quizViewModel.onSharingOptionChanged(it) },
                    selectedItem = uiState.value.sharingOption
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Questions: 3", //TODO actual number of questions
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W300,
                        color = white20,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)


                    )
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 50.dp)) {
                        Icon(Icons.Filled.Visibility, null)
                    }
                    IconButton(onClick = { navigator.navigate(NewQuestionDestination) }) {
                        Icon(Icons.Filled.Add, null)
                    }

                }


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
fun ChipGroup(
    list: List<SharingOption>,
    selectedItem: String,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow {
            items(list) {
                FilterChip(
                    modifier = Modifier.padding(4.dp),
                    selected = selectedItem == it.name,
                    onClick = { onSelectedChanged(it.name) },
                    leadingIcon = { Icon(it.icon, null, Modifier.alpha(0.7F)) },
                    label = { Text(text = it.name, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = lightGreen20),
                    shape = RoundedCornerShape(12.dp),
                    elevation = FilterChipDefaults.filterChipElevation(10.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
    optionList: List<String>,
    label: String,
    selectedOption: String,
    onSelectedOptionChanged: (String) -> Unit
) {

    var mExpanded by remember { mutableStateOf(false) }

    var mTextFieldSize by remember { mutableStateOf(0.dp) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(textFieldModifier) {

        OutlinedTextField(

            value = selectedOption,
            onValueChange = { onSelectedOptionChanged(it) },
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
                        onSelectedOptionChanged(label)
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
            .padding(start = 12.dp, top = 12.dp)

    ) {


        Card(
            border = BorderStroke(0.3.dp, black60),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .align(Alignment.TopStart),
            colors = CardDefaults.cardColors(containerColor = green60)
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
        ) {
            Icon(Icons.TwoTone.AddCircleOutline, contentDescription = null, tint = yellowPastel)
        }
    }
}
