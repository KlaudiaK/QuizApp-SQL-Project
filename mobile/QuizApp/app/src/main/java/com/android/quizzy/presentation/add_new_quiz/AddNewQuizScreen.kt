package com.android.quizzy.presentation.add_new_quiz

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.domain.model.DifficultyLevel
import com.android.quizzy.domain.model.PrivacySetting
import com.android.quizzy.presentation.destinations.NewQuestionDestination
import com.android.quizzy.presentation.destinations.QuestionListDestination
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuizViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val textFieldModifier = Modifier
    .fillMaxWidth()
    .padding(vertical = 8.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun quizDetailsTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    unfocusedBorderColor = orange20,
    textColor = white20,
    focusedBorderColor = sandYellow,
    focusedLabelColor = yellowPastel,
    unfocusedLabelColor = sandYellow,
    focusedTrailingIconColor = white20,
    unfocusedTrailingIconColor = white20
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Destination(route = "add_new_quiz")
@Composable
fun AddNewQuizScreen(
    navigator: DestinationsNavigator,
    viewModel: UiViewModel,
    quizViewModel: QuizViewModel = hiltViewModel(),
    isEditMode: Boolean = false,
    quizToEditID: Long? = null
) {
    if (isEditMode) {
        quizToEditID?.let { quizViewModel.getQuizInEditMode(it.toString()) }
        if (quizToEditID != null) {
            quizViewModel.getNumberOfQuestions(quizToEditID.toString())
        }
    }
    val uiState = quizViewModel.uiState
    val inputErrors = quizViewModel.inputErrors

    viewModel.onBottomBarVisibilityChange(false)

    val privacySettings = PrivacySetting.values().map {
        it.name
    }

    val difficultyLevels = DifficultyLevel.values().map {
        it.description
    }

    val scrollState = rememberScrollState()

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
    val context = LocalContext.current

    viewModel.onBottomBarVisibilityChange(false)

    val scope = rememberCoroutineScope()
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
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W400,
                            color = white20,
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 16.dp, end = 20.dp)
                                .align(Alignment.Start)
                        )
                        Row {
                            OutlinedTextField(
                                value = uiState.value.title,
                                onValueChange = {
                                    if (it.length < 40) quizViewModel.onTitleChanged(
                                        it
                                    )
                                },
                                modifier = Modifier
                                    .heightIn(max = 80.dp)
                                    .width(190.dp),
                                label = { Text("Title") },
                                colors = quizDetailsTextFieldColors(),
                                maxLines = 2,
                                isError = inputErrors.value.titleErrorId != null,
                                supportingText = {
                                    inputErrors.value.titleErrorId?.let {
                                        Text(stringResource(id = it))
                                    }
                                }
                            )
                            OutlinedTextField(
                                value = uiState.value.image,
                                onValueChange = { quizViewModel.onImageChanged(it) },
                                label = { Text("Image") },
                                colors = quizDetailsTextFieldColors(),
                                modifier = Modifier
                                    .heightIn(max = 80.dp)
                                    .padding(start = 8.dp),
                                trailingIcon = {
                                    IconButton(onClick = { startActivity(context, intent, null) }) {
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
                        }
                    }
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
                    optionList = uiState.value.categoriesList.map { it.name },
                    label = "Category",
                    selectedOption = uiState.value.category,
                    isError = inputErrors.value.categoryErrorId != null,
                    errorText = inputErrors.value.categoryErrorId
                ) { quizViewModel.onCategoryChanged(it) }

                CategoryDropDown(
                    optionList = privacySettings,
                    label = "Sharing option",
                    selectedOption = uiState.value.privacySettings,
                ) { quizViewModel.onPrivacySettingsChanged(it) }

                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = uiState.value.points.toString(),
                        onValueChange = {
                            if (it.isNotEmpty() && it.toInt() < 1000) quizViewModel.onPointsChanged(
                                it.toInt()
                            )
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .padding(end = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Points") },
                        colors = quizDetailsTextFieldColors(),
                        isError = inputErrors.value.pointsErrorId != null,
                        supportingText = {
                            inputErrors.value.pointsErrorId?.let {
                                Text(stringResource(id = it))
                            }
                        }
                    )
                    CategoryDropDown(
                        optionList = difficultyLevels,
                        label = "Difficulty level",
                        selectedOption = uiState.value.difficulty,
                        isError = inputErrors.value.difficultyErrorId != null,
                        errorText = inputErrors.value.difficultyErrorId,
                        modifier = Modifier.width(200.dp)
                    ) { quizViewModel.onDifficultyLevelChanged(it) }
                }



                ChipGroup(
                    list = PrivacySetting.values().toList(),
                    onSelectedChanged = { quizViewModel.onPrivacySettingsChanged(it) },
                    selectedItem = uiState.value.privacySettings
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Questions: ${uiState.value.numberOfQuestions}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W300,
                        color = white20,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                    )
                    quizToEditID?.let {
                        IconButton(
                            onClick = { navigator.navigate(QuestionListDestination(quizId = it)) },
                            modifier = Modifier.padding(start = 50.dp)
                        ) {
                            Icon(Icons.Filled.Visibility, null)
                        }
                    }

                    quizToEditID?.let { it1 ->
                        IconButton(onClick = { navigator.navigate(NewQuestionDestination(quizId = it1)) }) {
                            Icon(Icons.Filled.Add, null)
                        }
                    }

                }

                OutlinedButton(
                    onClick = {
                        if (isEditMode) {
                            quizToEditID?.let {
                                quizViewModel.editQuiz(it)
                            }
                            scope.launch {
                                delay(200)
                                navigator.navigateUp()
                            }
                        } else {
                            quizViewModel.onContinueClick {
                                navigator.navigateUp()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8F)
                        .padding(vertical = 24.dp)
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipGroup(
    list: List<PrivacySetting>,
    selectedItem: String,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        LazyRow {
            items(list) {
                FilterChip(
                    modifier = Modifier.padding(4.dp),
                    selected = selectedItem == it.name,
                    onClick = { onSelectedChanged(it.name) },
                    leadingIcon = { Icon(it.icon, null, Modifier.alpha(0.7F)) },
                    label = { Text(text = it.name, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = pastelBlue60),
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
    isError: Boolean = false,
    errorText: Int? = null,
    modifier: Modifier = textFieldModifier,
    onSelectedOptionChanged: (String) -> Unit,
) {

    var mExpanded by remember { mutableStateOf(false) }

    var mTextFieldSize by remember { mutableStateOf(0.dp) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier) {

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
            colors = quizDetailsTextFieldColors(),
            isError = isError,
            supportingText = {
                if (isError) {
                    errorText?.let {
                        Text(stringResource(id = it))
                    }
                }
            }
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

