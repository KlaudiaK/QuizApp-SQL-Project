package com.android.quizzy.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.presentation.destinations.CategoriesScreenDestination
import com.android.quizzy.presentation.destinations.InputValidationAutoDebounceScreenDestination
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.AppViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(route = "login", start = true)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: AppViewModel = hiltViewModel(),
    uiViewModel: UiViewModel
) {
    uiViewModel.onBottomBarVisibilityChange(false)
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black80)
            .padding(32.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to your Account",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 40.dp),
            color = pastelBlue60
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.value.email,
            onValueChange = {
                viewModel.onEvent(LoginScreenEvent.OnEmailTextFieldValueChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Username")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        if (uiState.value.incorrectEmail) {
            Text(
                text = "This username does't exist",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.value.password,
            onValueChange = {
                viewModel.onEvent(LoginScreenEvent.OnPasswordTextFieldValueChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        if (uiState.value.incorrectPassword) {
            Text(
                text = "Incorrect password",
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .width(250.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.onEvent(LoginScreenEvent.OnLoginButtonClicked)
                if (uiState.value.loggedIn) {
                    navigator.navigate(CategoriesScreenDestination)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = green60),
            enabled = uiState.value.email.isNotEmpty() && uiState.value.password.isNotEmpty()
        ) {
            Text(text = "Log in", fontSize = 18.sp, color = pastelBlack)
        }

        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navigator.navigate(InputValidationAutoDebounceScreenDestination);
                uiViewModel.onBottomBarVisibilityChange(false)
            }

        ) {
            Text(text = "Create new account", color = orange20)
        }

    }


}

