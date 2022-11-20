package com.android.quizzy.presentation.registration_form

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.android.quizzy.R
import com.android.quizzy.presentation.destinations.CategoriesScreenDestination
import com.android.quizzy.ui.theme.pastelBlack
import com.android.quizzy.ui.theme.pastelBlue20
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Destination(route = "registration", start = false)
@Composable
fun InputValidationAutoDebounceScreen(
    navigator: DestinationsNavigator,
    viewModel: InputValidationAutoDebounceViewModel = hiltViewModel(),
    UIviewModel: UiViewModel = hiltViewModel<UiViewModel>()
) {
    UIviewModel.onBottomBarVisibilityChange(true)
    LaunchedEffect(key1 = Unit) {

    }

    Log.i("VISIBILITY View", UIviewModel.uiState.value.toString())

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED,

            )
    }

    val username by viewModel.username.collectAsState()
    val firstname by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val repeatedPassword by viewModel.repeatedPassword.collectAsState()
    val areInputsValid by viewModel.areInputsValid.collectAsState()

    val creditCardNumberFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val firstnameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val repeatedPasswordFocusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.ShowToast -> {}//context.toast(event.messageId)
                is ScreenEvent.UpdateKeyboard -> {
                    if (event.show) keyboardController?.show() else keyboardController?.hide()
                }
                is ScreenEvent.ClearFocus -> focusManager.clearFocus()
                is ScreenEvent.RequestFocus -> {
                    when (event.textFieldKey) {
                        FocusedTextFieldKey.USERNAME -> usernameFocusRequester.requestFocus()
                        FocusedTextFieldKey.FIRSTNAME -> firstnameFocusRequester.requestFocus()
                        FocusedTextFieldKey.CREDIT_CARD_NUMBER -> creditCardNumberFocusRequester.requestFocus()
                        FocusedTextFieldKey.EMAIL -> emailFocusRequester.requestFocus()
                        FocusedTextFieldKey.PASSWORD -> passwordFocusRequester.requestFocus()
                        FocusedTextFieldKey.REPEATED_PASSWORD -> repeatedPasswordFocusRequester.requestFocus()
                        else -> {}
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            color = pastelBlack,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 40.dp),
        )
        CustomTextField(
            modifier = Modifier
                .focusRequester(usernameFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.USERNAME,
                        isFocused = focusState.isFocused
                    )
                }
                .fillMaxWidth(),
            labelResId = R.string.username,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            inputWrapper = username,
            onValueChange = viewModel::onUsernameEntered,
            onImeKeyAction = viewModel::onUsernameImeActionClick
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            modifier = Modifier
                .focusRequester(firstnameFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.FIRSTNAME,
                        isFocused = focusState.isFocused
                    )
                }
                .fillMaxWidth(),
            labelResId = R.string.name,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            inputWrapper = firstname,
            onValueChange = viewModel::onFirstnameEntered,
            onImeKeyAction = viewModel::onFirstnameImeActionClick
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            modifier = Modifier
                .focusRequester(emailFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.EMAIL,
                        isFocused = focusState.isFocused
                    )
                }
                .fillMaxWidth(),
            labelResId = R.string.email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            //visualTransformation = ::creditCardFilter,
            inputWrapper = email,
            onValueChange = viewModel::onEmailEntered,
            onImeKeyAction = viewModel::onEmailImeActionClick
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            modifier = Modifier
                .focusRequester(passwordFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.PASSWORD,
                        isFocused = focusState.isFocused
                    )
                }
                .fillMaxWidth(),
            labelResId = R.string.password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            inputWrapper = password,
            onValueChange = viewModel::onPasswordEntered,
            onImeKeyAction = viewModel::onPasswordImeActionClick
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            labelResId = R.string.repeated_password,
            modifier = Modifier
                .focusRequester(repeatedPasswordFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.REPEATED_PASSWORD,
                        isFocused = focusState.isFocused
                    )
                }
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            inputWrapper = repeatedPassword,
            onValueChange = viewModel::onRepeatedPasswordEntered,
            onImeKeyAction = viewModel::onContinueClick
        )
        Spacer(Modifier.height(32.dp))
        OutlinedButton(
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                viewModel::onContinueClick;
                navigator.popBackStack()
                navigator.navigate(CategoriesScreenDestination)
            },
            enabled = areInputsValid,
            colors = ButtonDefaults.buttonColors(containerColor = pastelBlue20),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Text(text = "Continue")
        }

    }

}
